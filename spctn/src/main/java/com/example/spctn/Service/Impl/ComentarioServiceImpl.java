package com.example.spctn.Service.Impl;

import com.example.spctn.Entity.Comment;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.ForbiddenException;
import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Repository.CommentRepository;
import com.example.spctn.Service.ComentarioService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final CommentRepository repository;
    private final UserServiceImpl userService;

    public ComentarioServiceImpl(CommentRepository repository,UserServiceImpl userService) {
        this.repository = repository;
        this.userService = userService;
    }


    @Override
    public List<Comment> findAllBySongId(Long SongId) {
    	

    	
    	List<Comment> comments = repository.findBySongId(SongId);
    	
    	if (comments == null ) {
			throw new ResourceNotFoundException("No comments found");
		}
          
    	return comments;
    }

    @Override
    public Comment save(Comment comentario) {

        return repository.save(comentario);
    }

    @Override
    public Comment update(Long id, Comment comment) {
    	
        if (comment==null || id==null) {
        	throw new BadRequestException("Song and id shoud not be null");
		}
    	
        Comment cm = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found"));

    	User user = userService.getAuthenticatedUser();
    	
    	
    	if (!cm.getUser().getId().equals(user.getId())) {
    	    throw new ForbiddenException("You cannot update this comment");
    	}


        
        
        if (comment.getTexto()==null) {
        	throw new BadRequestException("Comment shoud not be null");
		}
        cm.setTexto(comment.getTexto());
       
        

        return repository.save(cm);
    }
    
    @Override
    public void delete(Long commentId) {
   
    	if (commentId == null ) {
			throw new BadRequestException("comment id must not be null");
		}
    	
    	Comment comment = repository.findById(commentId).orElse(null);
    	User user = userService.getAuthenticatedUser();
    	if (comment ==null) {
			throw new ResourceNotFoundException("comment not found");
		}
    	
    	
    	if (!comment.getUser().getId().equals(user.getId())) {
    	    throw new ForbiddenException("You cannot delete this comment");
    	}
    	
        repository.deleteById(commentId);
    }
}