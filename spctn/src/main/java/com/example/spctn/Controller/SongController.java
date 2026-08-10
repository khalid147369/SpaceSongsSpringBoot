package com.example.spctn.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.spctn.Dto.Request.SearchSongRequestDTO;
import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Request.SongUpdateRequestDTO;
import com.example.spctn.Dto.Response.LikeResponseDTO;
import com.example.spctn.Dto.Response.SongDetailsDTO;
import com.example.spctn.Dto.Response.SongResponseDTO;

import com.example.spctn.Entity.Song;
import com.example.spctn.Mapper.LikeMapper;
import com.example.spctn.Mapper.SongMapper;
import com.example.spctn.Service.GeminiService;

import com.example.spctn.Service.SongService;
import com.example.spctn.Service.UserService;
import com.example.spctn.Service.Impl.MetricServiceImpl;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService service;
    private final SongMapper mapper;
    private final LikeMapper likeMapper;
    private final UserService userService;
    private final GeminiService geminiService;
    private final MetricServiceImpl metricServiceImpl;

    public SongController(SongService service,SongMapper mapper,LikeMapper likeMapper,UserService userService,GeminiService geminiService,MetricServiceImpl metricServiceImpl) {
        this.service = service;
        this.mapper = mapper;
        this.likeMapper = likeMapper;
        this.userService = userService;
        this.geminiService = geminiService;
        this.metricServiceImpl = metricServiceImpl;

    }

    /**
     * Devolver todos los canciones
     * @return
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<SongResponseDTO>> findAll(
    		@RequestParam(required = false) String titulo,
    		@RequestParam(required = false) Long category,
    		@RequestParam(required = false) Boolean isNew,
    		@RequestParam(required = false) String numEscuchas,
            @PageableDefault(page = 0, size = 20, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
    	Page<SongResponseDTO> songs =service.findAll(titulo,category,isNew,pageable).map(mapper::toResponse);
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
    	song.setTotalComments(metricServiceImpl.countTotalCommentsBySong(id));
    	song.setTotalSaves(metricServiceImpl.countTotalSavedSongsBySong(id));
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
     * @throws IOException 
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SongResponseDTO>  save(@Valid @ModelAttribute SongRequestDTO songDto) throws IOException {
    	
 
    	Song sn = service.save(songDto);
     
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(sn));
    }

    /**
     * Modificar una canción por su identificador
     * @param id
     * @param song
     * @return
     * @throws IOException 
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SongResponseDTO>  update(@PathVariable Long id,@Valid @ModelAttribute SongUpdateRequestDTO song) throws IOException {
    	
    	
    	
    	SongResponseDTO songdto =service.update(id, song);
        return ResponseEntity.ok(songdto);
    }

    /**
     * Borrar una canción por su identificador
     * @param id
     * @return
     * @throws IOException 
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id) throws IOException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping("/test-version")
    public ResponseEntity<String> testVersion() {
    	
        return ResponseEntity.ok("test");
    }
    
    @GetMapping("/trending")
    public ResponseEntity<Page<SongResponseDTO>> getTrendingThisWeek(
    		@RequestParam(required = false) Long category,
            @PageableDefault(page = 0, size = 10) Pageable pageable ){

        Page<SongResponseDTO> trendingSongs = service.getTrendingThisWeek(category,pageable)

                .map(mapper::toResponse);

        return ResponseEntity.ok(trendingSongs);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<SongResponseDTO>> findWithFilters(
            SearchSongRequestDTO filter, // 👈 SIN @RequestBody (Spring mapea los Query Params automáticamente)
            @PageableDefault(page = 0, size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {


        Page<SongResponseDTO> encountredSongs = service.findWithFilters(filter.getText(),filter.getCategory(),pageable)

                .map(mapper::toResponse);

        return ResponseEntity.ok(encountredSongs);
    }
}
