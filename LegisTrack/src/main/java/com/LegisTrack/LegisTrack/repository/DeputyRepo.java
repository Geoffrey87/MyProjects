package com.LegisTrack.LegisTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Deputy extends JpaRepository<Deputy, Long> {
}
