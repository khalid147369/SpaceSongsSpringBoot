package com.example.spctn.Mapper;


import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Response.*;
import com.example.spctn.Entity.Song;
import com.example.spctn.Service.UserService;


@Component
public class SongMapper {
	
	private final UserService userService;
	SongMapper(UserService userService){
	    this.userService=userService;		
	}
    public SongResponseDTO toResponse(Song song) {
    	
    	
    	
        SongResponseDTO dto = new SongResponseDTO();

        dto.setId(song.getId());
        dto.setTitulo(song.getTitulo());
        dto.setUrl(song.getUrl());


        dto.setImagen(song.getImagen());
        dto.setCreador(song.getCreador());
        dto.setTipo(song.getTipo());
        dto.setNumlikes(song.getNumlikes());

        return dto;
    }

    public Song toEntity(SongRequestDTO dto) {

        Song song = new Song();


        song.setTitulo(dto.getTitulo());
        song.setUrl(dto.getUrl());


        song.setImagen(dto.getImagen());
        song.setCreador(userService.getAuthenticatedUser().getId());
        song.setTipo(dto.getTipo());


        return song;
    }
}
