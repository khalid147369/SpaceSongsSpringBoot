package com.example.spctn.Controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Response.LikeResponseDTO;
import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Mapper.LikeMapper;
import com.example.spctn.Mapper.SongMapper;
import com.example.spctn.Service.SongService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService service;
    private final SongMapper mapper;
    private final LikeMapper likeMapper;

    public SongController(SongService service,SongMapper mapper,LikeMapper likeMapper) {
        this.service = service;
        this.mapper = mapper;
        this.likeMapper = likeMapper;
    }

    /**
     * Devolver todos los canciones
     * @return
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<SongResponseDTO>> findAll(
    		@RequestParam(required = false) String titulo,
    		@RequestParam(required = false) String tipo,
            @PageableDefault(page = 0, size = 20, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
    	List<SongResponseDTO> songs =service.findAll(titulo,tipo,pageable).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(songs) ;
    }

    /**
     * devolver la información de una canción por su identificador 
     * @param id
     * @return
     */
    @GetMapping("/getSingle/{id}")
    public ResponseEntity<SongResponseDTO> findById(@PathVariable Long id) {
    	SongResponseDTO song = mapper.toResponse(service.findById(id));
        return ResponseEntity.ok(song);
    }
    
    /**
     * devuelve el número de likes de una canción por su identificador
     * @param id
     * @return
     */
    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getCount(id));
    }
    
    /**
     * devuelve la información de los likes de una canción por su identificador
     * @param id
     * @return
     */
    @GetMapping("/{id}/likes")
    public ResponseEntity<List<LikeResponseDTO>> getLikes(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.getLikes(id).stream().map(likeMapper::toResponse).toList());
    }

    /**
     * Guardar una nueva canción
     * @param song
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SongResponseDTO>  save(@Valid @RequestBody SongRequestDTO song) {
    	
    	Song sn = service.save( mapper.toEntity(song));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(sn));
    }

    /**
     * Modificar una canción por su identificador
     * @param id
     * @param song
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SongResponseDTO>  update(@PathVariable Long id,@Valid @RequestBody SongRequestDTO song) {
    	
    	Song sng = mapper.toEntity(song);
    	
    	SongResponseDTO songdto = mapper.toResponse(service.update(id, sng));
        return ResponseEntity.ok(songdto);
    }

    /**
     * Borrar una canción por su identificador
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
