package com.example.spctn.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongResponseDTO {

    private Long id;
    private String title;
    private String audioUrl;
    private String cover;
    private String category;
    private String cartoon;
    private Long likes;
    private Long listens;
    private Double duration;
    private Integer year;
    private String description;
    private Boolean isNew;
    private Long creador;
    private String status;


}
