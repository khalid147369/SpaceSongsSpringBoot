
package com.example.spctn.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Request.CommentRequestDTO;
import com.example.spctn.Dto.Response.CommentResponseDTO;
import com.example.spctn.Entity.Comment;
import com.example.spctn.Mapper.CommentMapper;
import com.example.spctn.Service.ComentarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final ComentarioService service;
    private final CommentMapper mapper;

    public CommentController(ComentarioService service,CommentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * devuelve los comentarios de una canción determinada
     * @param songId
     * @return
     */
    @GetMapping("/{songId}")
    public ResponseEntity<List<CommentResponseDTO>>  findAll(@PathVariable Long songId) {
    	List<Comment> comments = service.findAllBySongId(songId);
    	
    	return ResponseEntity.ok(comments.stream().map(mapper::toResponse).toList());
    }
    
    @GetMapping("/userComments")
    public ResponseEntity<Page<CommentResponseDTO>>  findUserComments(   @PageableDefault(page = 0, size = 3) Pageable pageable ) {
    	Page<CommentResponseDTO> comments = service.getUserComments(pageable);
    	
    	return ResponseEntity.ok(comments);
    }


    /**
     * Agregar un comentario
     * @param comentario
     * @return
     */
    @PostMapping("/{songId}")
    public ResponseEntity<CommentResponseDTO> save(@PathVariable Long songId ,@Valid @RequestBody CommentRequestDTO comentario) {
    	
    	 Comment comment = service.save(mapper.toEntity(comentario,songId));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(comment));
    }
    /**
     * Update un comentario por su identificador
     * @param comentario
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(@PathVariable Long id,@Valid  @RequestBody CommentRequestDTO comentario) {
    	
    	 Comment comment = service.update(mapper.toEntity(comentario,id));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(comment));
    }
    

    /**
     * borrar un comentario por el identificador del comentario
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
