package com.example.spctn.Mapper;



import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.LikeRequestDTO;
import com.example.spctn.Dto.Response.LikeResponseDTO;
import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;
import com.example.spctn.Repository.SongRepository;
import com.example.spctn.Repository.UserRepository;
import com.example.spctn.Service.UserService;
import com.example.spctn.Exeption.BadRequestException;

@Component
public class LikeMapper {

	private final UserService userService;
	private final SongRepository songRepository;
	LikeMapper(UserService userService,SongRepository songRepository){
		this.userService=userService;
		this.songRepository=songRepository;
	}
	
    public LikeResponseDTO toResponse(Like like) {

        LikeResponseDTO dto = new LikeResponseDTO();

        dto.setId(like.getId());

        dto.setUserId(like.getUser().getId());
        dto.setUsuario(like.getUser().getNombre());
        
        dto.setTime(like.getFecha());
        
        dto.setSongId(like.getSong().getId());
        dto.setCancion(like.getSong().getTitulo());

        return dto;
    }

    public Like toEntity(Long songId) {
    	
    	
    	if (songId==null ) {
			throw new BadRequestException("song id not found");
		}
    	User user = userService.getAuthenticatedUser(); ;
    	Song song = songRepository.findById(songId).orElse(null);
    	
    	if (user==null || song==null) {
			throw new BadRequestException("user or song not found");
		}
    	
    	Like lk = new Like();
    	lk.setUser(user);
    	lk.setSong(song);
    	lk.setFecha(LocalDateTime.now());

        return lk;
    }
}