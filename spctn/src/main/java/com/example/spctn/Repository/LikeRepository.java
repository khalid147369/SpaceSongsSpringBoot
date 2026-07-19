package com.example.spctn.Repository;

import com.example.spctn.Entity.Like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
	boolean existsByUserIdAndSongId(Long userId, Long songId);
	List<Like> findBySongId(Long songId);
	
	long countBySongId(Long songId);
}
