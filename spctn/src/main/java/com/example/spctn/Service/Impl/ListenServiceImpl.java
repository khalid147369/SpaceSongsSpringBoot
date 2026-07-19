package com.example.spctn.Service.Impl;

import com.example.spctn.Entity.Listen;
import com.example.spctn.Repository.ListenRepository;
import com.example.spctn.Service.ListenService;

import com.example.spctn.Exeption.BadRequestException;

import org.springframework.stereotype.Service;


@Service
public class ListenServiceImpl implements ListenService {

    private final ListenRepository repository;

    public ListenServiceImpl(ListenRepository repository) {
        this.repository = repository;
    }


    @Override
    public Long findListenCountBySongId(Long SongId) {
    	
    	if (SongId == null) {
			throw new BadRequestException("listen id must not be null");
		}
        return repository.countBySongId(SongId);
    }

    @Override
    public Listen save(Listen listen) {
    	if (listen == null) {
			throw new BadRequestException("listen must not be null");
		}
        return repository.save(listen);
    }


}