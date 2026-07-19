package com.example.spctn.Dto.Request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListenRequestDTO {
	@NotNull
    private Long songId;
}
