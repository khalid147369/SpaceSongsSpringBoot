package com.example.spctn.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Request.UserRequestDTO;
import com.example.spctn.Dto.Response.UserResponseDTO;
import com.example.spctn.Entity.User;
import com.example.spctn.Mapper.UserMapper;
import com.example.spctn.Service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    public UserController(UserService service , UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Devolver todos los usuarios
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

    	List<UserResponseDTO> users = service.findAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(users)  ;
    }

    /**
     * Devolver un usuario por su identificador
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
    	UserResponseDTO us = mapper.toResponse(service.findById(id));
        return ResponseEntity.ok(us);
    }

    /**
     * Registrar un usuario
     * @param user
     * @return
     */
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO>  save(@Valid @RequestBody UserRequestDTO user) {
    	
			User us = service.save(mapper.toEntity(user));
    	
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(us));
	
    	
    }

    /**
     * Modificar un usuario por su identificador
     * @param id
     * @param user
     * @return
     */
    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserRequestDTO user) {

			UserResponseDTO us = mapper.toResponse(service.update(mapper.toEntity(user)));
			return ResponseEntity.ok(us);
	
       
    }

    /**
     * Borrar un usuario por su identificador
     */
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
