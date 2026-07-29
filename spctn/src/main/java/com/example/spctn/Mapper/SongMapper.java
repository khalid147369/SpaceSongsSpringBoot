package com.example.spctn.Mapper;



import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Response.*;
import com.example.spctn.Entity.Song;



@Component
public class SongMapper {
	
	
    public SongResponseDTO toResponse(Song song) {
    	
    	
    	
        SongResponseDTO dto = new SongResponseDTO();

        dto.setId(song.getId());
        dto.setTitle(song.getTitulo());
        dto.setAudioUrl(song.getUrl());      
        dto.setCover(song.getImagen());
        dto.setDuration(song.getDuracion());
        dto.setYear(song.getAnoEmision());
        dto.setListens(song.getNumEscuchas());
        dto.setLikes(song.getNumlikes());
        dto.setDescription(song.getDescripcion());
        dto.setIsNew(song.getIsNew());
        dto.setCategory(song.getCategory().getId());
        dto.setCreador(song.getCreador());
        dto.setTrivia(song.getTrivia());
        dto.setLanguage(song.getLanguage());
        dto.setAboutStory(song.getAboutStory());
        dto.setCartoon(song.getCartoon());
        
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
        song.setFechaCreacion(OffsetDateTime.now());


        return song;
    }
}
