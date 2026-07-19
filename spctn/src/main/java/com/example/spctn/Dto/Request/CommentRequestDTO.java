package com.example.spctn.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

	@NotBlank
	@Size(max = 100, message = "The text must not  exceed 100 characters")
    private String text;

	@NotNull
    private Long songId;

}
