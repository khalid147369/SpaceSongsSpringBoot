package com.example.spctn.Service.Impl;

import com.example.spctn.Entity.Listen;
import com.example.spctn.Repository.ListenRepository;
import com.example.spctn.Service.ListenService;

import com.example.spctn.Exeption.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ListenServiceImpl implements ListenService {

    private final ListenRepository repository;
    private final SongServiceImpl songService;
    
    public ListenServiceImpl(ListenRepository repository,SongServiceImpl songService) {
        this.repository = repository;
        this.songService = songService;
    }


    @Override
    public Long findListenCountBySongId(Long SongId) {
    	
    	if (SongId == null) {
			throw new BadRequestException("listen id must not be null");
		}
        return repository.countBySongId(SongId);
    }
    @Transactional
    @Override
    public Listen save(Listen listen) {
    	
    	Listen listenDetails = repository.save(listen);
        songService.incrementarEscuchas(listen.getSong().getId());
        return listenDetails; 
    }


}