package com.example.spctn.Mapper;




import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;


import com.example.spctn.Dto.Response.ListenResponseDTO;

import com.example.spctn.Entity.Listen;

import com.example.spctn.Service.SongService;
import com.example.spctn.Service.UserService;

@Component
public class ListenMapper {

	private final UserService userService;
	private final SongService songService;
	ListenMapper(UserService userService,SongService songService){
		this.userService=userService;
		this.songService=songService;
	}
	
	
    public ListenResponseDTO toResponse(Listen listen) {

        ListenResponseDTO dto = new ListenResponseDTO();

        dto.setId(listen.getId());

        dto.setUserId(listen.getUser().getId());
        dto.setUsuario(listen.getUser().getNombre());

        dto.setFecha(listen.getFecha());
        
        dto.setSongId(listen.getSong().getId());
        dto.setCancion(listen.getSong().getTitulo());

        return dto;
    }

    public Listen toEntity(Long songId) {

        Listen listen = new Listen();

        
        listen.setFecha(OffsetDateTime.now());
        listen.setUser(userService.getAuthenticatedUser());
        listen.setSong(songService.findById(songId));

        return listen;
    }
}