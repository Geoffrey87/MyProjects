package Memento.repositories;

import Memento.entities.ConnectionRequest;
import Memento.entities.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    Optional<ConnectionRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<ConnectionRequest> findByReceiverIdAndStatus(Long receiverId, RequestStatus status);
    List<ConnectionRequest> findBySenderId(Long senderId);
}

