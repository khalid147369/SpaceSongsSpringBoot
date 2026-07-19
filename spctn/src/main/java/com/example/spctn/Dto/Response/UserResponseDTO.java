package com.example.spctn.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private String fotoPerfil;
    private String Role;
}
