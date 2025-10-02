package Memento.repositories;

import Memento.entities.Follower;
import Memento.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    List<Follower> findByFollowing(User user);

    List<Follower> findByFollower(User user);

    Optional<Follower> findByFollowerAndFollowing(User follower, User following);

    boolean existsByFollowerAndFollowing(User follower, User following);
}
