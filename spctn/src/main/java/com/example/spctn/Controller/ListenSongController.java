
package com.example.spctn.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Response.ListenResponseDTO;
import com.example.spctn.Entity.Listen;
import com.example.spctn.Mapper.ListenMapper;
import com.example.spctn.Service.ListenService;
@RestController
@RequestMapping("/listens")
public class ListenSongController {

    private final ListenService service;
    private final ListenMapper mapper;

    public ListenSongController(ListenService service,ListenMapper mapper) {
        this.service = service;
        this.mapper = mapper; 
    }

    /**
     * Obtener número de escuchas de una canción
     * @param songId identificador de la canción
     */
    @GetMapping("/{songId}")
    public Long findById(@PathVariable Long songId) {
        return service.findListenCountBySongId(songId);
    }

    /**
     * Aumentar el número de escuchas de una canción
     * @param songId identificador de la canción
     * @return devuelve la información del aumento
     */
    @PostMapping("/{songId}")
    public ResponseEntity<ListenResponseDTO> save(@PathVariable Long songId) {
    	
    	Listen listenEntity = service.save(mapper.toEntity(songId));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(listenEntity));
    }

}