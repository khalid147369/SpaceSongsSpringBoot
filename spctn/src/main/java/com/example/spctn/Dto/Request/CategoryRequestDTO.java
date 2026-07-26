package com.example.spctn.Dto.Request;


import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequestDTO {
	@NotBlank
    private String name;
	@NotBlank
    private String description;
	@NotNull
    private MultipartFile imageFile;
}
