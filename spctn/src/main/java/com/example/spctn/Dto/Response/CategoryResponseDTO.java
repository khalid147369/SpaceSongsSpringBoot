package com.example.spctn.Dto.Response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private LocalDateTime updatedAt;
}
