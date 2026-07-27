package com.example.spctn.Dto.Response;


import java.time.OffsetDateTime;

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

    private OffsetDateTime Time;
}