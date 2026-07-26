package com.example.spctn.Repository;
import com.example.spctn.Entity.Listen;
import com.example.spctn.Entity.Song;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListenRepository extends JpaRepository<Listen, Long> {
	long countBySongId(Long songId);
	// Devuelve las entidades Song directamente ordenadas por las más escuchadas desde una fecha
    @Query("""
        SELECT l.song 
        FROM Listen l 
        WHERE l.fecha >= :desde 
        GROUP BY l.song 
        ORDER BY COUNT(l.id) DESC
    """)
    List<Song> findTrendingSongsSince(@Param("desde") LocalDateTime desde, Pageable pageable);
}
