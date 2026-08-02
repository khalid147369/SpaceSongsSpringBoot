package com.example.spctn.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Request.UserRequestDTO;
import com.example.spctn.Dto.Request.UserUpdateRequestDTO;
import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Dto.Response.UserResponseDTO;
import com.example.spctn.Entity.Category;
import com.example.spctn.Entity.User;
import com.example.spctn.Mapper.SongMapper;
import com.example.spctn.Mapper.UserMapper;
import com.example.spctn.Service.CategoryService;
import com.example.spctn.Service.UserService;
import com.example.spctn.Service.Impl.MetricServiceImpl;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final MetricServiceImpl metricService;
    private final UserMapper mapper;
    private final SongMapper songMapper;
    public UserController(UserService service , UserMapper mapper,SongMapper songMapper,MetricServiceImpl metricService) {
        this.service = service;
        this.mapper = mapper;
        this.songMapper = songMapper;
        this.metricService = metricService;

    }
    
   

    /**
     * Devolver todos los usuarios
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

    	List<UserResponseDTO> users = service.findAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Devolver un usuario por su identificador
     * @param id
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> findMe() {
    	UserResponseDTO us = mapper.toResponse(service.findById());
    	us.setTotalComments(metricService.countTotalCommentsByUser());
    	us.setTotalLikes(metricService.countTotalLikesByUser());
    	us.setTotalSongsSaved(metricService.countTotalSavedSongByUser());
        return ResponseEntity.ok(us);
    }
    
    @GetMapping("/recentlyPlayedSongs")
    public ResponseEntity<Page<SongResponseDTO>> favoriteSongs(
    		@PageableDefault(page = 0, size = 20) Pageable pageable) {
    	Page<SongResponseDTO> songs = service.lastSongPlayed(pageable).map(songMapper::toResponse);
        return ResponseEntity.ok(songs);
    }

    /**
     * Registrar un usuario
     * @param user
     * @return
     * @throws IOException 
     */
    @PostMapping(value="/auth/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDTO>  save(@Valid @ModelAttribute UserRequestDTO user) throws IOException {
    	
    	UserResponseDTO us = service.save(user);
    	
			return ResponseEntity.status(HttpStatus.CREATED).body(us);
	
    	
    }

    /**
     * Modificar un usuario por su identificador
     * @param id
     * @param user
     * @return
     * @throws IOException 
     */
    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@Valid @ModelAttribute UserUpdateRequestDTO user) throws IOException {


			UserResponseDTO us = mapper.toResponse(service.update(user));
			
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
