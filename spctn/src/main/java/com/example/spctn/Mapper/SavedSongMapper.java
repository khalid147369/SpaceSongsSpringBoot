package com.example.spctn.Mapper;






import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;


import com.example.spctn.Dto.Response.SavedSongResponseDTO;
import com.example.spctn.Entity.SavedSong;

import com.example.spctn.Service.SongService;
import com.example.spctn.Service.UserService;


@Component
public class SavedSongMapper {
	private final UserService userService;
	private final SongService songService;
	SavedSongMapper(UserService userService,SongService songService){
		this.userService=userService;
		this.songService=songService;
	}
    public SavedSongResponseDTO toResponse(SavedSong guardado) {

    	SavedSongResponseDTO dto = new SavedSongResponseDTO();

    	dto.setId(guardado.getId());
    	  dto.setUserId(guardado.getUser().getId());
          dto.setUsuario(guardado.getUser().getNombre());

          dto.setSongId(guardado.getSong().getId());
          dto.setCancion(guardado.getSong().getTitulo());

        

        return dto;
    }

    public SavedSong toEntity(Long songId) {

    	SavedSong guardado = new SavedSong();
    	Long userId = userService.getAuthenticatedUser().getId();
        guardado.setUser(userService.findById());
        guardado.setSong(songService.findById(songId));
        guardado.setFecha(OffsetDateTime.now());
        
      
        return guardado;
    }
}