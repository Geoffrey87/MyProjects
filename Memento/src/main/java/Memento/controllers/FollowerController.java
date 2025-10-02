package Memento.controllers;

import Memento.dtos.InputDto.FollowRequestDto;
import Memento.dtos.OutputDto.FollowerOutputDto;
import Memento.entities.User;
import Memento.security.SecurityUserDetails;
import Memento.services.IFollowerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followers")
public class FollowerController {

    private final IFollowerService followerService;

    public FollowerController(IFollowerService followerService) {
        this.followerService = followerService;
    }

    // ---------------------------
    // FOLLOW / UNFOLLOW
    // ---------------------------

    @PostMapping("/follow")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FollowerOutputDto> followUser(
            @Valid @RequestBody FollowRequestDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        User follower = userDetails.getUser();
        FollowerOutputDto created = followerService.follow(dto, follower);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/unfollow/{targetUserId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        User follower = userDetails.getUser();
        followerService.unfollow(targetUserId, follower);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------
    // GET FOLLOWERS / FOLLOWING
    // ---------------------------

    @GetMapping("/my-followers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FollowerOutputDto>> getMyFollowers(
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        List<FollowerOutputDto> followers = followerService.getFollowers(user);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/my-following")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FollowerOutputDto>> getMyFollowing(
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        List<FollowerOutputDto> following = followerService.getFollowing(user);
        return ResponseEntity.ok(following);
    }
}

