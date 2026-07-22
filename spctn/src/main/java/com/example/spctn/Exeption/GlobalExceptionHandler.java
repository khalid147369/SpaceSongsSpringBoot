package com.example.spctn.Exeption;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

	  @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex){
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<String> handleBadRequest(BadRequestException ex){
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(DuplicateResourceException.class)
	    public ResponseEntity<String> handleDuplicate(DuplicateResourceException ex){
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(UnauthorizedException.class)
	    public ResponseEntity<String> handleUnauthorized(UnauthorizedException ex){
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(ForbiddenException.class)
	    public ResponseEntity<String> handleForbidden(ForbiddenException ex){
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneral(Exception ex){
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Ha producido un error interno: "+ex.getMessage()); 
	    }
	    
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        
	        // 2. Extrae el primer error y obtiene solo el mensaje limpio
	        String errorMessage = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(error -> error.getDefaultMessage())
	                .findFirst()
	                .orElse("Validation error");

	        // 3. Devuelve un estado 400 (Bad Request) con tu mensaje
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(errorMessage);
	    }
	    
	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<String> handleValidationExceptions(BadCredentialsException ex) {
	        
	        // 2. Extrae el primer error y obtiene solo el mensaje limpio
	        String errorMessage = ex.getMessage();

	        // 3. Devuelve un estado 400 (Bad Request) con tu mensaje
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(errorMessage);
	    }
	    
	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
	        Map<String, Object> respuesta = new HashMap<>();
	        respuesta.put("timestamp", LocalDateTime.now());
	        respuesta.put("status", HttpStatus.FORBIDDEN.value());
	        respuesta.put("error", "Forbidden");
	        respuesta.put("message", "No tienes permisos suficientes para realizar esta acción (Se requiere rol ADMIN).");
	        
	        return new ResponseEntity<>(respuesta, HttpStatus.FORBIDDEN); // Devuelve un 403 limpio
	    }
}
