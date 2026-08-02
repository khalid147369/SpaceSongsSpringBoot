package com.example.spctn.Repository;

import com.example.spctn.Entity.Comment;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findBySongId(Long songId);
	
	long countBySongId(Long songId);
	
	long countByUserId(Long songId);
	
	// 💬 Misma consulta escrita en JPQL
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.fecha DESC")
    Page<Comment> findCommentsByUserId(@Param("userId") Long userId, Pageable pageable);
}
