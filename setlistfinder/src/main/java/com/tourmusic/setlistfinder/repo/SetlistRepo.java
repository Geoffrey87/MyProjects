package com.tourmusic.setlistfinder.repo;

import com.tourmusic.setlistfinder.entitys.Setlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetlistRepo extends JpaRepository<Setlist, Long> {
}
