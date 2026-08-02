package com.example.spctn.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.spctn.Dto.Response.CommentResponseDTO;
import com.example.spctn.Entity.Comment;

public interface ComentarioService {

		Comment update( Comment comment);
		
	     List<Comment> findAllBySongId(Long id);

	    Comment save(Comment comentario);

	    void delete(Long id);

		Page<CommentResponseDTO> getUserComments(Pageable pageable);

}
