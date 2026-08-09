package com.example.spctn.Dto.Request;


import org.springframework.web.multipart.MultipartFile;

import com.example.spctn.Enums.SongStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongUpdateRequestDTO {


	@Size(max = 40, message = "The title must not exceed 40 characters")
    private String title;
    
    
    private String cartoon;
    
	
    private Long category;
	
	 
	private String description;
	
	private SongStatus status;
	

	private String trivia;
	
	
	private String aboutStory;
	
	
	private String language;
	
	
	private MultipartFile audioFile;
	
	
	private MultipartFile imageFile;


}