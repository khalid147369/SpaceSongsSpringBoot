package com.example.spctn.Repository;

import com.example.spctn.Entity.SavedSong;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedSongRepository extends JpaRepository<SavedSong, Long> {
	List<SavedSong> findByUserId(Long userId);
	
	boolean existsByUserIdAndSongId(Long userId,Long songId);
	Optional<SavedSong> findByUserIdAndSongId(Long userId,Long songId);
}
