package Memento.services.impl;

import Memento.dtos.InputDto.FollowRequestDto;
import Memento.dtos.OutputDto.FollowerOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Follower;
import Memento.entities.User;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.FollowerMapper;
import Memento.repositories.FollowerRepository;
import Memento.repositories.UserRepository;
import Memento.services.IFollowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FollowerService implements IFollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    public FollowerService(FollowerRepository followerRepository,
                           UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FollowerOutputDto follow(FollowRequestDto dto, User followerUser) {
        User followingUser = userRepository.findById(dto.getTargetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getTargetUserId()));

        // Prevent duplicate follow
        if (followerRepository.existsByFollowerAndFollowing(followerUser, followingUser)) {
            throw new IllegalStateException("You are already following this user");
        }

        Follower follower = new Follower();
        follower.setFollower(followerUser);
        follower.setFollowing(followingUser);
        follower.setSince(LocalDate.now());

        Follower saved = followerRepository.save(follower);

        return FollowerMapper.domainToDto(saved,
                new FollowerOutputDto(),
                new UserSummaryDto(),
                new UserSummaryDto());
    }

    @Override
    public void unfollow(Long targetUserId, User followerUser) {
        User followingUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + targetUserId));

        Follower follower = followerRepository.findByFollowerAndFollowing(followerUser, followingUser)
                .orElseThrow(() -> new ResourceNotFoundException("Follow relationship not found"));

        followerRepository.delete(follower);
    }

    @Override
    public List<FollowerOutputDto> getFollowers(User user) {
        List<Follower> followers = followerRepository.findByFollowing(user);
        List<FollowerOutputDto> result = new ArrayList<>();

        for (Follower f : followers) {
            FollowerOutputDto dto = new FollowerOutputDto();
            FollowerMapper.domainToDto(f, dto, new UserSummaryDto(), new UserSummaryDto());
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<FollowerOutputDto> getFollowing(User user) {
        List<Follower> following = followerRepository.findByFollower(user);
        List<FollowerOutputDto> result = new ArrayList<>();

        for (Follower f : following) {
            FollowerOutputDto dto = new FollowerOutputDto();
            FollowerMapper.domainToDto(f, dto, new UserSummaryDto(), new UserSummaryDto());
            result.add(dto);
        }

        return result;
    }
}

