package com.LegisTrack.LegisTrack.repository;

import com.LegisTrack.LegisTrack.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepo extends JpaRepository<Vote, Long> {
    List<Vote> findByLawId(Long lawId);

    boolean existsByLawIdAndPartyId(Long lawId, Long partyId);
}
