package com.example.spctn.Service.Impl;

import com.example.spctn.Entity.Like;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;
import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Repository.LikeRepository;
import com.example.spctn.Service.LikeService;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository repository;
    private final SongServiceImpl songService;


    public LikeServiceImpl(LikeRepository repository,SongServiceImpl songService) {
        this.repository = repository;
        this.songService = songService;
    }

    @Transactional
    @Override
    public Like save(Like like) {
    	if (like==null) {
			throw new BadRequestException("like must not be null");
		}
    	
    	if (like.getUser() == null || like.getSong() == null) {
    	    throw new BadRequestException("User and Song are required");
    	}
    	
    	boolean exists = repository.existsByUserIdAndSongId(like.getUser().getId(), like.getSong().getId());
    	
    	if (exists) {
			throw new DuplicateResourceException("you already liked the song");
		}
    	
    	Like savedLike = repository.save(like);
    	
    	songService.incrementarLikes(like.getSong().getId());
    	
        return savedLike;
    }

    @Transactional
    @Override
    public void delete(Long id) {
    	if (id==null) {
			throw new BadRequestException("Id must not be null");
		}
    	Like like = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No like found"));
    	songService.decrementarLikes(like.getSong().getId());
        repository.deleteById(id);
    }
    
    @Override
    public Like findLikeById(Long id) {
    	if (id==null) {
			throw new BadRequestException("Id must not be null");
		}
    	Like like = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("like not found") );

    	return like;
    }

}