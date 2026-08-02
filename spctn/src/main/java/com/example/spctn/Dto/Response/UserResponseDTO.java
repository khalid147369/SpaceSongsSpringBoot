package com.example.spctn.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private String fotoPerfil;
    private String Role;
    private String descreption;
    private String categoryName;
    
    private Long totalLikes;
    private Long totalSongsSaved;
    private Long totalComments;
}
