package com.example.spctn.Security;

import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
	    this.jwtFilter = jwtFilter;
	}
	
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
        		.cors(Customizer.withDefaults()) 
                .csrf(csrf -> csrf.disable())
                
	                .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .exceptionHandling(exception -> exception
	                        .authenticationEntryPoint((request, response, authException) -> {
	                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	                        })
	                        .accessDeniedHandler((request, response, accessDeniedException) -> {
	                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
	                        })
	                    )
                .authorizeHttpRequests(auth -> auth
                     .requestMatchers(
                    		 	"/auth/**",
                    		 	"/categories/getAll",
                    		 	"/categories/getById/{id}",
                    		 	"/users/auth/register",
                    		 	"/songs/search",
                    		 	"/songs/getAll",
                    		 	"/songs/test-version",
                    		 	"/songs/trending",
                    	        "/songs/getSingle/*",
                    	        "/songs/*/comments",
                    	        "/songs/*/likes/count").permitAll()
                     .anyRequest().authenticated())
                        
                .addFilterBefore(
	                jwtFilter,
	                UsernamePasswordAuthenticationFilter.class);

                

        return http.build();
    }
}
