package com.example.spctn.Repository;

import com.example.spctn.Entity.Song;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT COUNT(s) FROM Song s WHERE s.category.id = :categoryId AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED")
    long countByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT s FROM Song s WHERE s.titulo = :titulo AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED")
    Optional<Song> findByTitulo(@Param("titulo") String titulo);

    @Query("SELECT s FROM Song s WHERE s.id = :id")
    Optional<Song> findById(@Param("id") Long id);

    @Query("SELECT s FROM Song s WHERE s.category.id = :categoryId AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED")
    List<Song> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT s FROM Song s WHERE s.fechaCreacion >= :limitDate AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED ORDER BY s.fechaCreacion DESC")
    Page<Song> findNewSongs(@Param("limitDate") OffsetDateTime limitDate, Pageable pageable);

    @Query("SELECT s FROM Song s")
    Page<Song> findAll(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Song s WHERE s.titulo = :titulo AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED")
    boolean existsByTitulo(@Param("titulo") String titulo);

    @Query("SELECT s FROM Song s WHERE LOWER(s.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED")
    Page<Song> findByTituloContainingIgnoreCase(@Param("titulo") String titulo, Pageable pageable);

    @Query("SELECT s FROM Song s WHERE s.category.id = :categoryId AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED")
    Page<Song> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("""
        SELECT s
        FROM Song s
        WHERE (
            :text IS NULL OR
            s.titulo LIKE CONCAT('%', :text, '%') OR
            s.cartoon LIKE CONCAT('%', :text, '%')
        )
        AND (:categoryId IS NULL OR s.category.id = :categoryId)
        AND s.estado = com.example.spctn.Enums.SongStatus.PUBLISHED
    """)
    Page<Song> findWithFilters(
        @Param("text") String text,
        @Param("categoryId") Long categoryId,
        Pageable pageable
    );
}