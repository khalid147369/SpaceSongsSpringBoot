package com.example.spctn.Entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.OffsetDateTime;


@Getter
@Setter
@Entity
public class SavedSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime fecha;

    @ManyToOne
    private User user;

    @ManyToOne
    private Song song;

}
