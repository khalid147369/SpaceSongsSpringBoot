package com.example.spctn.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    private String fotoPerfil;
    
    private String fotoPerfilPublicId;
    
    private String description="I grew up watching classic cartoons. Every opening theme is a time machine back to Saturday mornings.";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @ColumnDefault("1")
    private Category favoriteCategory=new Category(1L);
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    @OneToMany(mappedBy = "user")
    private List<SavedSong> savedSongs;

    @OneToMany(mappedBy = "user")
    private List<Listen> listens;

}
