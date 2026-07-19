package com.example.spctn.Entity;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String url;

    private String imagen;

    private String tipo;
    
    private Long creador;
    
    private Long numlikes;

    @OneToMany(mappedBy = "song")
    private List<Comment> comments;

    @OneToMany(mappedBy = "song")
    private List<Like> likes;

    @OneToMany(mappedBy = "song")
    private List<SavedSong> savedSongs;

    @OneToMany(mappedBy = "song")
    private List<Listen> listens;


}
