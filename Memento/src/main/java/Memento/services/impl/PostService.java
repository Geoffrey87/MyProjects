package Memento.services.impl;

import Memento.dtos.InputDto.PostCreateDto;
import Memento.dtos.OutputDto.PostOutputDto;
import Memento.entities.*;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.PostMapper;
import Memento.repositories.*;
import Memento.services.IPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional // this annotation ensures that all operations are wrapped in a database transaction - ACID compliance
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final MediaRepository mediaRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       TagRepository tagRepository,
                       MediaRepository mediaRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public PostOutputDto create(PostCreateDto dto, String authorEmail) {
        // Find the author of the post by email (from JWT)
        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + authorEmail));

        // Load tags if provided
        Set<Tag> tags = new HashSet<>();
        if (dto.getTagIds() != null) {
            Iterable<Tag> foundTags = tagRepository.findAllById(dto.getTagIds());
            for (Tag t : foundTags) {
                tags.add(t);
            }
        }

        // Load media if provided
        Set<Media> media = new HashSet<>();
        if (dto.getMediaIds() != null) {
            Iterable<Media> foundMedia = mediaRepository.findAllById(dto.getMediaIds());
            for (Media m : foundMedia) {
                media.add(m);
            }
        }

        // Map DTO data to a new Post entity
        Post post = new Post();
        PostMapper.dtoToDomain(dto, post, author, tags, media);

        // Ensure bidirectional relationship for media
        for (Media m : media) {
            m.setPost(post);
        }

        // Save post in database
        Post saved = postRepository.save(post);

        // Convert entity back to output DTO
        PostOutputDto output = new PostOutputDto();
        PostMapper.domainToDto(saved, output);
        return output;
    }

    @Override
    public PostOutputDto update(Long id, PostCreateDto dto, String authorEmail) {
        // Find existing post
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Only the author can update their own post
        if (!post.getAuthor().getEmail().equals(authorEmail)) {
            throw new SecurityException("You are not allowed to update this post");
        }

        // Load tags if provided
        Set<Tag> tags = new HashSet<>();
        if (dto.getTagIds() != null) {
            Iterable<Tag> foundTags = tagRepository.findAllById(dto.getTagIds());
            for (Tag t : foundTags) {
                tags.add(t);
            }
        }

        // Map DTO data to the existing post (without media for now)
        PostMapper.dtoToDomain(dto, post, post.getAuthor(), tags);

        // Save updated post
        Post updated = postRepository.save(post);

        // Convert entity back to output DTO
        PostOutputDto output = new PostOutputDto();
        PostMapper.domainToDto(updated, output);
        return output;
    }

    @Override
    public void delete(Long id, String authorEmail) {
        // Find existing post
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Only the author can delete their own post
        if (!post.getAuthor().getEmail().equals(authorEmail)) {
            throw new SecurityException("You are not allowed to delete this post");
        }

        // Delete post
        postRepository.delete(post);
    }

    @Override
    public PostOutputDto getById(Long id) {
        // Find post by ID
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Convert entity to output DTO
        PostOutputDto output = new PostOutputDto();
        PostMapper.domainToDto(post, output);
        return output;
    }

    @Override
    public List<PostOutputDto> getTimeline(String viewerEmail) {
        // For now: return all posts (later can filter by visibility & relationships)
        List<Post> posts = postRepository.findAll();
        List<PostOutputDto> result = new ArrayList<>();

        for (Post post : posts) {
            PostOutputDto dto = new PostOutputDto();
            PostMapper.domainToDto(post, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<PostOutputDto> getByUser(Long userId, String viewerEmail) {
        // Get all posts from a specific user
        List<Post> posts = postRepository.findByAuthorId(userId);
        List<PostOutputDto> result = new ArrayList<>();

        for (Post post : posts) {
            PostOutputDto dto = new PostOutputDto();
            PostMapper.domainToDto(post, dto);
            result.add(dto);
        }

        return result;
    }
}
