package com.example.spctn.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    // Lee la propiedad app.cors.allowed-origins del application.properties
    // Si la propiedad no existe por algún motivo, usará http://localhost:5173 por defecto
    @Value("${app.cors.allowed-origins:http://localhost:3000}")
    private List<String> allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. Asigna los orígenes leídos desde application.properties
        config.setAllowedOrigins(allowedOrigins);

        // 2. Métodos HTTP permitidos para tu API REST
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 3. Encabezados permitidos en las peticiones
        config.setAllowedHeaders(List.of(
            "Authorization", 
            "Content-Type", 
            "X-Requested-With", 
            "Accept", 
            "Origin"
        ));

        // 4. Encabezados expuestos que el cliente JS puede leer
        config.setExposedHeaders(List.of("Authorization", "X-Total-Count"));

        // 5. Permitir credenciales/cookies/tokens
        config.setAllowCredentials(true);

        // 6. Tiempo de caché para peticiones OPTIONS (1 hora)
        config.setMaxAge(3600L);

        // Aplicar a todos los endpoints del API REST
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}