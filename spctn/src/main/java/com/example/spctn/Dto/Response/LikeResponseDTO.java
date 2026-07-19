package com.example.spctn.Dto.Response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDTO {

    private Long id;

    private Long userId;
    private String usuario;

    private Long songId;
    private String cancion;

    private LocalDateTime Time;
}