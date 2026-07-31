package com.example.spctn.Repository;


import com.example.spctn.Entity.Song;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SongRepository extends JpaRepository<Song, Long> {
	@EntityGraph(attributePaths = {"category"})
	Optional<Song> findByTitulo(String titulo);
	
	@EntityGraph(attributePaths = {"category"})
	List<Song> findByCategoryId(Long CategoryId);
	
	@EntityGraph(attributePaths = {"category"})
	boolean existsByTitulo(String titulo);
	
	@EntityGraph(attributePaths = {"category"})
	Page<Song> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	
	@EntityGraph(attributePaths = {"category"})
	Page<Song> findByCategoryId(Long categoryId, Pageable pageable);
}