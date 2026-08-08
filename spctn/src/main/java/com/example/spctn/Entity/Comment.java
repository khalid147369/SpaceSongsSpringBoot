package com.example.spctn.Entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.OffsetDateTime;

import com.example.spctn.Enums.CommentStatus;


@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    private OffsetDateTime fecha;
    
    private CommentStatus estado=CommentStatus.PUBLISHED;

    @ManyToOne
    private User user;

    @ManyToOne
    private Song song;

}
