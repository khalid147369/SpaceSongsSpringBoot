package com.example.spctn.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserByIdResponseDTO {

    private Long id;
    private String nombre;
    private String fotoPerfil;
    private String descreption;
    private String categoryName;
    
    private Long totalLikes;
    private Long totalSongsSaved;
    private Long totalComments;
}
