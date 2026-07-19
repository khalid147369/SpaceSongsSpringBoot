package com.example.spctn.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.spctn.Dto.Response.LoginResponseDTO;
import com.example.spctn.Entity.RefreshToken;
import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Repository.RefreshTokenRepository;
import com.example.spctn.Repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDurationMs; // Define esto en tu application.properties (ej: 604800000 para 7 días)

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
 

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository ,JwtService jwtService,UserDetailsService userDetailsService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public RefreshToken createRefreshToken(Long userId) {
    	
    	// 1. Buscamos si el usuario ya tiene un token viejo guardado y lo eliminamos
        refreshTokenRepository.deleteByUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    	
        RefreshToken refreshToken = new RefreshToken();
        
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please sign in again.");
        }
        return token;
    }
    
    public Optional<RefreshToken>  findByToken(String token) {
    	return refreshTokenRepository.findByToken(token);

    }

    public String refreshAccessToken(String token) {
        String refreshtoken = findByToken(token)
                .map(this::verifyExpiration)
                .map(tk -> {
                    // Cargamos los detalles del usuario asociado al Refresh Token
                    var userDetails = userDetailsService.loadUserByUsername(tk.getUser().getEmail());
                    
                    // Generamos un nuevo Access Token (JWT) limpio
                    return jwtService.generateToken(userDetails);

                })
                .orElseThrow(() -> new ResourceNotFoundException(""));
        return refreshtoken;
    }
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}