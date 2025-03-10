package com.LegisTrack.LegisTrack.repository;

import com.LegisTrack.LegisTrack.entity.Law;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawRepo extends JpaRepository<Law, Long> {
}
