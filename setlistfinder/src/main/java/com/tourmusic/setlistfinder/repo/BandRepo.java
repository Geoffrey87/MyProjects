package com.tourmusic.setlistfinder.repo;

import com.tourmusic.setlistfinder.entitys.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BandRepo extends JpaRepository<Band, Long> {
    Optional<Band> findByName(String name);
}
