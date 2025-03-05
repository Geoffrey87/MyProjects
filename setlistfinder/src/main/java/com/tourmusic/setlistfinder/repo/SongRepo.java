package com.tourmusic.setlistfinder.repo;

import com.tourmusic.setlistfinder.entitys.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {
}
