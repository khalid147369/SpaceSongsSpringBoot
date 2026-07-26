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
	@Size(max = 50, message = "The cartoon name must not exceed 50 characters")
	private String cartoon;
	
    
    @NotNull
    private Integer releseYear;
    
    
    @NotBlank
	@Size(max = 200, message = "The description must not exceed 200 characters")
    private String description;
    
	@NotNull
    private Long category;
	
	
	@NotNull
	private MultipartFile audioFile;
	
	@NotNull
	private MultipartFile imageFile;


}