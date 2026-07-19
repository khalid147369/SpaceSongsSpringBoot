package com.example.spctn.Mapper;



import java.time.LocalDateTime;


import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.CommentRequestDTO;
import com.example.spctn.Dto.Response.CommentResponseDTO;
import com.example.spctn.Entity.Comment;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;
import com.example.spctn.Service.SongService;
import com.example.spctn.Service.UserService;

@Component
public class CommentMapper {

	private final UserService userService;
	private final SongService songService;
	CommentMapper(UserService userService,SongService songService){
		this.userService=userService;
		this.songService=songService;
	}
	
    public CommentResponseDTO toResponse(Comment comentario) {

    	CommentResponseDTO dto = new CommentResponseDTO();

        dto.setId(comentario.getId());
        dto.setText(comentario.getTexto());
        dto.setDate(comentario.getFecha());
        dto.setCreator(comentario.getUser().getNombre());
        dto.setUserId(comentario.getUser().getId());
        dto.setSongId(comentario.getSong().getId());

        return dto;
    }

    public Comment toEntity(CommentRequestDTO dto) {

    	User user = userService.getAuthenticatedUser();
    	Song song = songService.findById(dto.getSongId());
    	
    	Comment comentario = new Comment();

        comentario.setTexto(dto.getText());
        comentario.setFecha(LocalDateTime.now());
        comentario.setUser(user);
        comentario.setSong(song);

        return comentario;
    }
}