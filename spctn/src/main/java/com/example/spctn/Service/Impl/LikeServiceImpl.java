package com.example.spctn.Service.Impl;

import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;
import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Mapper.SongMapper;
import com.example.spctn.Repository.LikeRepository;
import com.example.spctn.Service.LikeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository repository;
    private final SongServiceImpl songService;
    private final UserServiceImpl userService;
    private final SongMapper songMapper;


    public LikeServiceImpl(LikeRepository repository,SongServiceImpl songService,UserServiceImpl userService,SongMapper songMapper) {
        this.repository = repository;
        this.songService = songService;
        this.userService = userService;
        this.songMapper = songMapper;
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
    	Long userId = userService.getAuthenticatedUser().getId();
    	Boolean likeExists = repository.existsByUserIdAndSongId(userId,id);
    	
    	if (!likeExists) {
			throw new ResourceNotFoundException("Like not found");
		}
    	songService.decrementarLikes(id);
        repository.deleteByUserIdAndSongId(userId,id);
    }
    
    @Override
    public Like findLikeById(Long id) {
    	if (id==null) {
			throw new BadRequestException("Id must not be null");
		}
    	Like like = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("like not found") );

    	return like;
    }
    
    @Override
    public SongResponseDTO findLastLikedSong() {
    	Long userId = userService.getAuthenticatedUser().getId();
    	Song song = repository.findLastLikedSongByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("song not found") );
    	
    	return songMapper.toResponse(song);
    }
    
    @Override
    public Page<SongResponseDTO> findMostLikedSongs(Pageable pageable) {
        // 1. Obtenemos la página de entidades 'Song' desde la base de datos
        Page<Song> songsPage = repository.findMostLikedSongs(pageable);
        
        // 2. Mapeamos cada 'Song' a 'SongResponseDTO' usando el método .map() de Page
        return songsPage.map(songMapper::toResponse);
    }

}