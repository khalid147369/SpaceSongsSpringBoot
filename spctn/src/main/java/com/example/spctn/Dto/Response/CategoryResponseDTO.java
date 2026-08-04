package com.example.spctn.Dto.Response;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class CategoryResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private OffsetDateTime updatedAt;
    private Long songCount;
}
