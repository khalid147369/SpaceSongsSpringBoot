package com.example.spctn.Repository;


import com.example.spctn.Entity.Song;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SongRepository extends JpaRepository<Song, Long> {
	
	long countByCategoryId(Long categoryId);
	
	Optional<Song> findByTitulo(String titulo);
	
	Optional<Song> findById(Long id);
	
	List<Song> findByCategoryId(Long CategoryId);
	
	Page<Song> findByIsNew(Boolean isNew, Pageable pageable);
	
	Page<Song> findAll(Pageable pageable);
	
	
	boolean existsByTitulo(String titulo);
	
	Page<Song> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	
	Page<Song> findByCategoryId(Long categoryId, Pageable pageable);
	
	
	@Query("""
			SELECT s
			FROM Song s
			WHERE (
			    (:text IS NULL OR
			     s.titulo LIKE CONCAT('%', :text, '%') OR
			     s.cartoon LIKE CONCAT('%', :text, '%'))
			)
			AND (:categoryId IS NULL OR s.category.id = :categoryId)
			""")
			Page<Song> findWithFilters(
			    @Param("text") String text,
			    @Param("categoryId") Long categoryId,
			    Pageable pageable
			);
}