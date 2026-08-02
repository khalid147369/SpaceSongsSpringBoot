package com.example.spctn.Dto.Response;


import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {

	    private Long id;
	    private String text;
	    private String creator;
	    private String songName;
	    private String avatar;
	    private OffsetDateTime date;
	    private Long userId;
	    private Long songId;


}
