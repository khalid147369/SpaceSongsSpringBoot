package com.example.spctn.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedSongResponseDTO {

    private Long id;

    private Long userId;
    private String usuario;

    private Long songId;
    private String cancion;
}
