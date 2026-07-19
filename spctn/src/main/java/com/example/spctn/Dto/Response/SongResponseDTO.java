package com.example.spctn.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongResponseDTO {

    private Long id;
    private String titulo;
    private String url;
    private String imagen;
    private String tipo;
    private Long numlikes;
    private Long creador;


}
