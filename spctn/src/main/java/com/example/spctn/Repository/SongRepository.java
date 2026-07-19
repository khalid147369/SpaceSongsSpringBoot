package com.example.spctn.Repository;


import com.example.spctn.Entity.Song;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SongRepository extends JpaRepository<Song, Long> {
	
	Optional<Song> findByTitulo(String titulo);
	
	List<Song> findByTipo(String tipo);
	
	boolean existsByTitulo(String titulo);
	
	Page<Song> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	Page<Song> findByTipoContainingIgnoreCase(String tipo, Pageable pageable);
}