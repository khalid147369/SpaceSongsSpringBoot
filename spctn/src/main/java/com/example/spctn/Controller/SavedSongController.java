
package com.example.spctn.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Request.SavedSongRequestDTO;
import com.example.spctn.Dto.Response.SavedSongResponseDTO;
import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Entity.SavedSong;
import com.example.spctn.Mapper.SavedSongMapper;
import com.example.spctn.Mapper.SongMapper;
import com.example.spctn.Service.SavedSongService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/savedSongs")
public class SavedSongController {

    private final SavedSongService service;
    private final SongMapper songMapper;
    private final SavedSongMapper savedSongMapper;

    public SavedSongController(SavedSongService service,SongMapper songMapper,SavedSongMapper savedSongMapper) {
        this.service = service;
        this.songMapper = songMapper;
        this.savedSongMapper = savedSongMapper;
    }

    /**
     * Devolver las canciones guardados de cada usuario
     * @return
     */
    @GetMapping
    public ResponseEntity<List<SongResponseDTO>>  findAll() {
    	List<SongResponseDTO> savedSongs = service.findAll().stream().map(savedSong -> songMapper.toResponse(savedSong.getSong())).toList();
        return ResponseEntity.ok(savedSongs);
    }

    /**
     * Guardar canciones por usuario
     * @param guardado
     * @return
     */
    @PostMapping
    public ResponseEntity<SavedSongResponseDTO>  save(@Valid @RequestBody SavedSongRequestDTO guardado) {
    	SavedSong savedSong = service.save(savedSongMapper.toEntity(guardado));
        return ResponseEntity.ok(savedSongMapper.toResponse(savedSong)) ;
    }

    /**
     * Borrar canciones guardadas por el identificador de la canción
     * @param songId
     */
    @DeleteMapping("/{songId}")
    public void delete(@PathVariable Long songId) {
        service.delete(songId);
    }
}