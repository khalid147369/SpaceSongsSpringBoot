
package com.example.spctn.Controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.spctn.Dto.Response.LikeResponseDTO;
import com.example.spctn.Entity.Like;

import com.example.spctn.Mapper.LikeMapper;

import com.example.spctn.Service.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService service;
    private final LikeMapper mapper;


    
    public LikeController(LikeService service,LikeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
        
    }

    /**
     * Aumentar el número de likes por identificador de la canción
     * @param songId identificador de la canción
     * @return información sobre el like aumentado
     */
    @PostMapping("/{songId}")
    public ResponseEntity<LikeResponseDTO> save(@PathVariable Long songId){
    	
    	Like lk = service.save(mapper.toEntity(songId));
    	LikeResponseDTO likeResponse = mapper.toResponse(lk); 
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse);
    }

    /**
     * Borrar un like por su identificador 
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        service.delete(id);
        
    }
}