package com.example.spctn.Mapper;





import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.SavedSongRequestDTO;
import com.example.spctn.Dto.Response.SavedSongResponseDTO;
import com.example.spctn.Entity.SavedSong;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;
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

    public SavedSong toEntity(SavedSongRequestDTO savedsong) {

    	SavedSong guardado = new SavedSong();
    	Long userId = userService.getAuthenticatedUser().getId();
        guardado.setUser(userService.findById(userId));
        guardado.setSong(songService.findById(savedsong.getSongId()));
        guardado.setFecha(LocalDateTime.now());
        
      
        return guardado;
    }
}