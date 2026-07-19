package com.example.spctn.Repository;

import com.example.spctn.Entity.Comment;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findBySongId(Long songId);
}
