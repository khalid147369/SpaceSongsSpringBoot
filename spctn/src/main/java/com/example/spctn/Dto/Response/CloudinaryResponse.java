package com.example.spctn.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CloudinaryResponse {
	private String url;
    private Double duration; // Cloudinary lo devuelve en segundos (ej: 214.53)
}
