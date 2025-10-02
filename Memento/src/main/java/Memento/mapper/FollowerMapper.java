package Memento.mapper;

import Memento.dtos.InputDto.FollowRequestDto;
import Memento.dtos.OutputDto.FollowerOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Follower;
import Memento.entities.User;

import java.util.Objects;

public class FollowerMapper {

    public static FollowerOutputDto domainToDto(Follower follower,
                                                FollowerOutputDto dto,
                                                UserSummaryDto followerDto,
                                                UserSummaryDto followingDto) {
        Objects.requireNonNull(follower, "follower must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(follower.getId());
        dto.setSince(follower.getSince());

        // follower → UserSummaryDto
        if (follower.getFollower() != null && followerDto != null) {
            User followerUser = follower.getFollower();
            followerDto.setId(followerUser.getId());
            followerDto.setName(followerUser.getName());
            followerDto.setAvatarUrl(followerUser.getAvatarUrl());
            dto.setFollower(followerDto);
        }

        // following → UserSummaryDto
        if (follower.getFollowing() != null && followingDto != null) {
            User followingUser = follower.getFollowing();
            followingDto.setId(followingUser.getId());
            followingDto.setName(followingUser.getName());
            followingDto.setAvatarUrl(followingUser.getAvatarUrl());
            dto.setFollowing(followingDto);
        }

        return dto;
    }

    public static Follower dtoToDomain(FollowRequestDto dto,
                                       Follower follower,
                                       User followerUser,
                                       User followingUser) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(follower, "follower must not be null");
        Objects.requireNonNull(followerUser, "followerUser must not be null");
        Objects.requireNonNull(followingUser, "followingUser must not be null");

        follower.setFollower(followerUser);
        follower.setFollowing(followingUser);

        return follower;
    }
}
