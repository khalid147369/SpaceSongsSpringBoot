package com.example.spctn.Dto.Response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {

	    private Long id;
	    private String text;
	    private String creator;
	    private LocalDateTime date;
	    private Long userId;
	    private Long songId;


}
