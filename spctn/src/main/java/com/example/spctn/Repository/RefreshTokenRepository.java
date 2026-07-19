package com.example.spctn.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.spctn.Entity.RefreshToken;
import com.example.spctn.Entity.User;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    
    @Modifying // Le dice a Spring que esta consulta modifica la base de datos (DELETE/UPDATE)
    @Transactional // Abre la transacción necesaria justo para este borrado
    void deleteByUser(User user);
}
