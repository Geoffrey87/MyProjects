package Memento.services;

import Memento.dtos.InputDto.FollowRequestDto;
import Memento.dtos.OutputDto.FollowerOutputDto;
import Memento.entities.User;

import java.util.List;

public interface IFollowerService {

    /**
     * Follow another user.
     *
     * @param dto follow request containing the target user ID
     * @param follower the user who is following
     * @return the created follower relationship
     */
    FollowerOutputDto follow(FollowRequestDto dto, User follower);

    /**
     * Unfollow another user.
     *
     * @param targetUserId the ID of the user to unfollow
     * @param follower the user performing the unfollow
     */
    void unfollow(Long targetUserId, User follower);

    /**
     * Get all followers of a user.
     *
     * @param user the target user
     * @return list of followers
     */
    List<FollowerOutputDto> getFollowers(User user);

    /**
     * Get all users that a given user is following.
     *
     * @param user the source user
     * @return list of followings
     */
    List<FollowerOutputDto> getFollowing(User user);
}

