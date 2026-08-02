package com.example.spctn.Dto.Request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {

	@Size(max = 20, message = "The name must not exceed 20 characters")
    private String nombre;
	@Email
    private String email;
	
	@Size(min = 6, message = "The password must contain at least 6 characters")
    private String password;
	
    private MultipartFile fotoPerfil;
    
	@Size(max = 220, message = "The descreption must not exceed 220 characters")
    private String descreption;
    
    private Long category;
}
