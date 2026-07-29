package com.example.spctn.Dto.Request;


import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongRequestDTO {

	@NotBlank
	@Size(max = 40, message = "The title must not exceed 40 characters")
    private String title;
    
    @NotBlank
    private String cartoon;
    
	@NotNull
    private Long category;
	
	
	@NotNull
	private MultipartFile audioFile;
	
	@NotNull
	private MultipartFile imageFile;


}