package Memento.repositories;

import Memento.entities.ConnectionRequest;
import Memento.entities.User;
import Memento.entities.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    List<ConnectionRequest> findByReceiver(User receiver);
    List<ConnectionRequest> findBySender(User sender);
}

