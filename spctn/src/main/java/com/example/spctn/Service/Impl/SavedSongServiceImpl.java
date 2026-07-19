package com.example.spctn.Service.Impl;

import com.example.spctn.Entity.SavedSong;
import com.example.spctn.Repository.SavedSongRepository;

import com.example.spctn.Service.SavedSongService;

import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;
import com.example.spctn.Exeption.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedSongServiceImpl implements SavedSongService {

    private final SavedSongRepository repository;
    private final UserServiceImpl userService;

    public SavedSongServiceImpl(SavedSongRepository repository,UserServiceImpl userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public SavedSong save(SavedSong guardado) {
    	boolean exists = repository.existsByUserIdAndSongId(userService.getAuthenticatedUser().getId(), guardado.getSong().getId());
    	if (exists) {
			throw new DuplicateResourceException("Song already saved");
		}
        return repository.save(guardado);
    }

    @Override
    public void delete(Long songId) {

    	if (songId==null) {
			throw new BadRequestException("id not found");
		}
    	SavedSong savedSong = repository.findByUserIdAndSongId(userService.getAuthenticatedUser().getId(), songId).orElseThrow(()-> new ResourceNotFoundException("Song not found"));
        repository.delete(savedSong);
    }

    @Override
    public List<SavedSong> findAll() {
        return repository.findByUserId(userService.getAuthenticatedUser().getId());
    }
}