package com.example.spctn.Service;

import java.util.List;

import com.example.spctn.Entity.Comment;

public interface ComentarioService {

		Comment update(Long id, Comment comment);
		
	     List<Comment> findAllBySongId(Long id);

	    Comment save(Comment comentario);

	    void delete(Long id);

}
