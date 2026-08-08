package com.example.spctn.Repository;

import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
	boolean existsByUserIdAndSongId(Long userId, Long songId);
	
	void deleteByUserIdAndSongId(Long userId, Long songId);

	List<Like> findBySongId(Long songId);
	
	long countBySongId(Long songId);
	
	long countByUserId(Long userId);
	
	@Query("SELECT l.song FROM Like l WHERE l.user.id = :userId ORDER BY l.fecha DESC LIMIT 1")
    Optional<Song> findLastLikedSongByUserId(@Param("userId") Long userId);
	
	@Query("""
	        SELECT l.song 
	        FROM Like l 
	        GROUP BY l.song 
	        ORDER BY COUNT(l.id) DESC
	    """)
	    Page<Song> findMostLikedSongs(Pageable pageable);
}
