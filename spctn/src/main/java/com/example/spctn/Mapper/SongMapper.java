package com.example.spctn.Mapper;


import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Response.*;
import com.example.spctn.Entity.Song;
import com.example.spctn.Service.UserService;


@Component
public class SongMapper {
	
	
    public SongResponseDTO toResponse(Song song) {
    	
    	
    	
        SongResponseDTO dto = new SongResponseDTO();

        dto.setId(song.getId());
        dto.setTitle(song.getTitulo());
        dto.setAudioUrl(song.getUrl());
        dto.setCartoon(song.getCartoon());
        dto.setCover(song.getImagen());
        dto.setDuration(song.getDuracion());
        dto.setYear(song.getAnoEmision());
        dto.setListens(song.getNumEscuchas());
        dto.setLikes(song.getNumlikes());
        dto.setDescription(song.getDescripcion());
        dto.setIsNew(song.getIsNew());
        dto.setCategory(song.getTipo());
        dto.setCreador(song.getCreador());
        
        // quita las condiciones después 
        if (song.getEstado()!= null) {
			dto.setStatus(song.getEstado().toString());
		}else {
			dto.setStatus(null);
		}
        

       
        

        return dto;
    }

    public Song toEntity(SongRequestDTO dto) {

        Song song = new Song();


        song.setTitulo(dto.getTitle());
        song.setUrl(dto.getUrl());


        song.setImagen(dto.getImage());
        song.setTipo(dto.getCategory());
        song.setAnoEmision(dto.getReleseYear());
        song.setCartoon(dto.getCartoon());
        song.setDescripcion(dto.getDescription());
        song.setFechaCreacion(LocalDateTime.now());
        song.setDuracion(dto.getDuration());


        return song;
    }
}
