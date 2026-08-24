package com.example.spctn.Mapper;




import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;


import com.example.spctn.Dto.Response.ListenResponseDTO;

import com.example.spctn.Entity.Listen;
import com.example.spctn.Exeption.ResourceNotFoundException;
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

        //el user puede ser null aquí
        if (listen.getUser()!=null) {
			dto.setUserId(listen.getUser().getId());
        dto.setUsuario(listen.getUser().getNombre());
		}
        dto.setId(listen.getId());

        

        dto.setFecha(listen.getFecha());
        
        dto.setSongId(listen.getSong().getId());
        dto.setCancion(listen.getSong().getTitulo());

        return dto;
    }

    public Listen toEntity(Long songId) {

        Listen listen = new Listen();

        
        listen.setFecha(OffsetDateTime.now());
        
        //establecer el user null si no inició sesión
        try {
        	listen.setUser(userService.getAuthenticatedUser());
		} catch (ResourceNotFoundException e) {
			listen.setUser(null);
		}
        
        listen.setSong(songService.findById(songId));

        return listen;
    }
}