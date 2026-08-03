package com.example.spctn.Dto.Request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchSongRequestDTO {

	@NotBlank
	@Size(max = 40, message = "The text must not exceed 40 characters")
    private String text;

    

    private Long category;



}