package com.LegisTrack.LegisTrack.repository;

import com.LegisTrack.LegisTrack.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepo extends JpaRepository<Party, Long> {
    List<Party> findByUserId(String userId);
}
