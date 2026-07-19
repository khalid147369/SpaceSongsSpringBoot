package com.example.spctn.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

	@NotBlank
	@Size(max = 20, message = "The name must not exceed 20 characters")
    private String nombre;
	@Email
	@NotBlank
    private String email;
	
	@NotBlank(message = "The password is required")
	@Size(min = 6, message = "The password must contain at least 6 characters")
    private String password;
	
    private String fotoPerfil;
}
