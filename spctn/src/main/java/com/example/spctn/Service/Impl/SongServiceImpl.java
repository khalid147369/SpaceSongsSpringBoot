package com.example.spctn.Service.Impl;


import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Request.SongUpdateRequestDTO;
import com.example.spctn.Dto.Response.CloudinaryResponse;
import com.example.spctn.Dto.Response.SongDetailsDTO;
import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Entity.Category;
import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;
import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Mapper.SongMapper;
import com.example.spctn.Repository.CategoryRepository;
import com.example.spctn.Repository.LikeRepository;
import com.example.spctn.Repository.ListenRepository;
import com.example.spctn.Repository.SongRepository;
import com.example.spctn.Service.GeminiService;
import com.example.spctn.Service.SongService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository repository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;
    private final ListenRepository listenRepository;
    private final UserServiceImpl userService;
    private final CloudinaryService cloudinaryService;
    private final GeminiService geminiService;
    private final SongMapper mapper;

    public SongServiceImpl(SongRepository repository,LikeRepository likeRepository,ListenRepository listenRepository,CloudinaryService cloudinaryService,UserServiceImpl userService,SongMapper mapper,CategoryRepository categoryRepository,GeminiService geminiService) {
        this.repository = repository;
        this.likeRepository = likeRepository;
        this.listenRepository = listenRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.geminiService=geminiService;
    }

    public Page<Song> findAll(String title,Long category,Boolean isNew,Pageable pageable) {
    	 
    	if (title != null && !title.isEmpty()) {
            return repository.findByTituloContainingIgnoreCase(title, pageable);
        }
    	if (category != null) {
            return repository.findByCategoryId(category, pageable);
        }
    	if (isNew != null && isNew==true) {
    		OffsetDateTime limitDate = OffsetDateTime.now().minusDays(3);
            return repository.findNewSongs(limitDate, pageable);
        }
        return repository.findAll(pageable);
    }

    public Song findById(Long id) {
    	
    	Song song = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found") );
    	
        return song;
    }
    
    public Long getCount(Long id) {
    	
    	if (id==null) {
			throw new ResourceNotFoundException("id not found");
		}
    	
    	Long count = likeRepository.countBySongId(id);

        return count;
    }
    
    public Long incrementarLikes(Long id) {
    	Song song = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found") );

    	if (song.getNumlikes()==null) {
    		song.setNumlikes(0L);
		}
    	Long Incrementedlikes =song.getNumlikes() + 1;
    	
    	song.setNumlikes(Incrementedlikes);
    	repository.save(song);
    	
    	return Incrementedlikes ;
    }
    
    public Long incrementarEscuchas(Long id) {
    	Song song = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found") );

    	if (song.getNumEscuchas()==null) {
    		song.setNumEscuchas(0L);
		}
    	Long IncrementedEscuchas =song.getNumEscuchas() + 1;
    	
    	song.setNumEscuchas(IncrementedEscuchas);
    	repository.save(song);
    	
    	return IncrementedEscuchas ;
    }
    
    public Long decrementarLikes(Long id) {
    	Song song = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found"));

    	if (song.getNumlikes()==null) {
    		song.setNumlikes(0L);
		}
    	if (song.getNumlikes()==0) {
    		throw new BadRequestException("Likes is already 0");
		}
    	Long decrementedlikes =song.getNumlikes() - 1;
    	
    	song.setNumlikes(decrementedlikes);
    	repository.save(song);
    	
    	return decrementedlikes ;
    }
    

    
    public List<Like> getLikes(Long id) {
    	if (id==null) {
			throw new ResourceNotFoundException("id must not be null");
		}
    	List<Like> likes = likeRepository.findBySongId(id);

    	if (likes==null) {
			throw new ResourceNotFoundException("no likes found");
		}
        return likes;
    }

    public Song save(SongRequestDTO songDto) throws IOException  {

 

        
       	CloudinaryResponse audioResponse = cloudinaryService.uploadFile(songDto.getAudioFile(), "canciones");
    	CloudinaryResponse imageResponse = cloudinaryService.uploadFile(songDto.getImageFile(), "portadas");
    	
    	String urlAudio = audioResponse.getUrl();
    	String urlImagen = imageResponse.getUrl();
        Double duracion = audioResponse.getDuration();
        String audioPublicId = audioResponse.getPublicId();
        String imagePublicId = imageResponse.getPublicId();

       

        Category category = categoryRepository.findById(songDto.getCategory()).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    	
    	Long userId =userService.getAuthenticatedUser().getId();
    	Song sngToSave = mapper.toEntity(songDto);
    	
    	boolean existe = repository.existsByTitulo(sngToSave.getTitulo());	
    	
    	if (existe) {
    		throw new DuplicateResourceException("Song already exist");
        }
    	sngToSave.setCreador(userId);
    	sngToSave.setCategory(category);
    	sngToSave.setImagen(urlImagen);
    	sngToSave.setUrl(urlAudio);
    	sngToSave.setDuracion(duracion);
    	sngToSave.setImagePublicId(imagePublicId);
    	sngToSave.setAudioPublicId(audioPublicId);
    	
    	//Ai genaration
    	SongDetailsDTO datos = geminiService.generateFullSongDetails(sngToSave.getTitulo(), sngToSave.getCategory().getNombre(),sngToSave.getCartoon());
    	
    	sngToSave.setTrivia(datos.trivia());
    	sngToSave.setAboutStory(datos.aboutStory());
    	sngToSave.setDescripcion(datos.description());
    	sngToSave.setLanguage(datos.language());
    	sngToSave.setAnoEmision(datos.year());
    	
      
        return repository.save(sngToSave);
        
    }

    public SongResponseDTO update(Long id, SongUpdateRequestDTO song) throws IOException {
    	
        if (song==null || id==null) {
        	throw new BadRequestException("Song and id shoud not be null");
		}
    	
        Song sn = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found"));
        

        
        
        
        if (song.getImageFile()!=null) {
        	if (sn.getImagePublicId() != null) {
        	    cloudinaryService.deleteFile(sn.getImagePublicId(),"image");
        	}
        	CloudinaryResponse imageResponse = cloudinaryService.uploadFile(song.getImageFile(), "portadas");
        	String urlImagen = imageResponse.getUrl();
        	String publicId = imageResponse.getPublicId();
			 sn.setImagen(urlImagen);
			 sn.setImagePublicId(publicId);
		}
        
        if (song.getAudioFile()!=null) {
        	if (sn.getAudioPublicId() != null) {
        	    cloudinaryService.deleteFile(sn.getAudioPublicId(),"video");
        	}
        	CloudinaryResponse audioResponse = cloudinaryService.uploadFile(song.getAudioFile(), "canciones");
        	String urlAudio = audioResponse.getUrl();
        	String publicId = audioResponse.getPublicId();
			 sn.setUrl(urlAudio);
			 sn.setAudioPublicId(publicId);
		}
        
        if (song.getTitle()!=null) {
			 sn.setTitulo(song.getTitle());
		}
        
        
        if (song.getCategory()!=null) {
        	 Category category = categoryRepository.findById(song.getCategory()).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
			 sn.setCategory(category);
		}
        
        if (song.getTrivia()!=null) {
			 sn.setTrivia(song.getTrivia());
		}
        
        if (song.getCartoon()!=null) {
			 sn.setCartoon(song.getCartoon());
		}
        
        if (song.getAboutStory()!=null) {
			 sn.setAboutStory(song.getAboutStory());
		}
        
        if (song.getDescription()!=null) {
			 sn.setDescripcion(song.getDescription());
		}
        
        if (song.getLanguage()!=null) {
			 sn.setLanguage(song.getLanguage());
		}
        if (song.getStatus()!=null) {
			 sn.setEstado(song.getStatus());
		}
        

        return mapper.toResponse(repository.save(sn)) ;
    }

    public void delete(Long id) throws IOException {
    	if (id==null) {
        	throw new BadRequestException("id shoud not be null");
		}
        Song sn = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found"));

    	

         	if (sn.getImagePublicId() != null) {
         	    cloudinaryService.deleteFile(sn.getImagePublicId(),"image");
         	}
         
         	if (sn.getAudioPublicId() != null) {
         	    cloudinaryService.deleteFile(sn.getAudioPublicId(),"video");
         	}
         	
        repository.deleteById(id);
    }
    

    public Page<Song> getTrendingThisWeek(@RequestParam(required = false) Long categoryId ,int limit) {
    	OffsetDateTime haceUnaSemana = OffsetDateTime.now().minusDays(14);
        Pageable pageable = PageRequest.of(0, limit);
            
        return listenRepository.findTrendingSongsSince(haceUnaSemana,categoryId, pageable);
    }

	@Override
	public Page<Song> findWithFilters(String text, Long categoryId, Pageable pageable) {

		return repository.findWithFilters(text, categoryId, pageable);
	}


    
}
