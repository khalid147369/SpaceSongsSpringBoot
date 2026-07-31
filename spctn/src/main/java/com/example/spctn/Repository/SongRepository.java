package com.example.spctn.Repository;


import com.example.spctn.Entity.Song;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SongRepository extends JpaRepository<Song, Long> {
	
	Optional<Song> findByTitulo(String titulo);
	
	Optional<Song> findById(Long id);
	
	List<Song> findByCategoryId(Long CategoryId);
	
	Page<Song> findAll(Pageable pageable);
	
	boolean existsByTitulo(String titulo);
	
	Page<Song> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	
	Page<Song> findByCategoryId(Long categoryId, Pageable pageable);
}