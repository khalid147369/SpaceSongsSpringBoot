package com.example.spctn.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // Ej: "Planeta Acción", "Planeta Deportes"

    private String descripcion; // Ej: "The planets of your childhood"

    private String imageUrl; // URL de la imagen del planeta/categoría

    private OffsetDateTime updatedAt; // Mantiene el rastro de "Updated 2d ago"

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
    
    public Category(Long id) {
        this.id = id;
    }
}
