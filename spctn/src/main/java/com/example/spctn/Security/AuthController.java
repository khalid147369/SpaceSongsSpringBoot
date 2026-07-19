package com.example.spctn.Security;


import com.example.spctn.Dto.Request.LoginRequestDTO;
import com.example.spctn.Dto.Response.LoginResponseDTO;
import com.example.spctn.Service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService; // 1. Inyectamos tu nuevo servicio

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService,
            RefreshTokenService refreshTokenService,
            UserService userService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),
                        request.getPassword()

                )

        );

        var userDetails =
                userDetailsService.loadUserByUsername(request.getEmail());
        
        String token = jwtService.generateToken(userDetails);
        
        Long userId = userService.findByEmail(request.getEmail()).getId();
        
     // 3. Creamos el Refresh Token en la base de datos
        var refreshToken = refreshTokenService.createRefreshToken(userId);
        
     // 4. Creamos la Cookie HttpOnly
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
                .path("/auth")     // Solo se envía a las rutas de autenticación
                .maxAge(7 * 24 * 60 * 60) // 7 días de duración
                .httpOnly(true)        // JavaScript no puede leerla
                .secure(false)         // Cámbialo a true cuando uses HTTPS en producción
                .sameSite("Strict")
                .build();
        

     // 5. Devolvemos el JWT en el JSON del body y la Cookie en las cabeceras
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new LoginResponseDTO(token));
    }
    
    
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(
            @CookieValue(name = "refreshToken", required = false) String requestRefreshToken) {
        
        if (requestRefreshToken == null || requestRefreshToken.isEmpty()) {
            throw new RuntimeException("Refresh Token is missing in cookies!");
        }
        String newAccessToken = refreshTokenService.refreshAccessToken(requestRefreshToken);
        return  ResponseEntity.ok(new LoginResponseDTO(newAccessToken));
                
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue(name = "refreshToken", required = false) String requestRefreshToken) {
        
        // 1. Si la cookie existe, buscamos el token y lo borramos de la Base de Datos
        if (requestRefreshToken != null && !requestRefreshToken.isEmpty()) {
            refreshTokenService.findByToken(requestRefreshToken)
                    .ifPresent(token -> refreshTokenService.deleteByUserId(token.getUser().getId()));
        }

        // 2. Creamos una cookie idéntica pero con maxAge(0) para obligar al navegador a borrarla
        ResponseCookie deleteRefreshCookie = ResponseCookie.from("refreshToken", "")
                .path("/api/auth")
                .maxAge(0)          // Al ponerlo en 0, el navegador la elimina inmediatamente
                .httpOnly(true)
                .secure(false)      // Ponlo en true en producción con HTTPS
                .sameSite("Strict")
                .build();

        // 3. Respondemos con la cabecera Set-Cookie para limpiar el navegador
        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.SET_COOKIE, deleteRefreshCookie.toString())
                .body("Cierre de sesión exitoso. Cookie eliminada.");
    }

}
