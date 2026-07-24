package com.example.spctn.Entity;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.example.spctn.Enums.SongStatus;

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

    private String cartoon;
    
    @Enumerated(EnumType.STRING)
    private SongStatus estado=SongStatus.DRAFT;
    
    private String tipo;
    
    private String descripcion;
    
    private Long creador;
    
    private Long numlikes;
    
    private Long numEscuchas;
    
    private Double duracion;
    
    private Integer anoEmision;
    
    private LocalDateTime fechaCreacion;

 // Método que calcula si la canción tiene menos de 14 días de lanzada
    public boolean getIsNew() {
        if (this.fechaCreacion == null) return false;
        
        // Define tu regla: por ejemplo, 14 días
        LocalDateTime limitDate = LocalDateTime.now().minusDays(3);
        
        return this.fechaCreacion.isAfter(limitDate);
    }
    
    @OneToMany(mappedBy = "song")
    private List<Comment> comments;

    @OneToMany(mappedBy = "song")
    private List<Like> likes;

    @OneToMany(mappedBy = "song")
    private List<SavedSong> savedSongs;

    @OneToMany(mappedBy = "song")
    private List<Listen> listens;


}
