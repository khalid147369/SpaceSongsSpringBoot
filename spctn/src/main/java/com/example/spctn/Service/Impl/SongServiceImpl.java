package com.example.spctn.Service.Impl;


import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;
import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Repository.LikeRepository;
import com.example.spctn.Repository.SongRepository;
import com.example.spctn.Service.SongService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository repository;
    private final LikeRepository likeRepository;

    public SongServiceImpl(SongRepository repository,LikeRepository likeRepository) {
        this.repository = repository;
        this.likeRepository = likeRepository;
    }

    public Page<Song> findAll(String title,String tipo,Pageable pageable) {
    	 
    	if (title != null && !title.isEmpty()) {
            return repository.findByTituloContainingIgnoreCase(title, pageable);
        }
    	if (tipo != null && !tipo.isEmpty()) {
            return repository.findByTipoContainingIgnoreCase(tipo, pageable);
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

    public Song save(Song song) {

    	if (song==null) {
        	throw new BadRequestException("Song shoud not be null");
		}
 
    	boolean existe = repository.existsByTitulo(song.getTitulo());	
    	
    	if (existe) {
    		throw new DuplicateResourceException("Song already exist");
        }
        
      
        Song sn = new Song();
  
        if (song.getImagen()!=null) {
			 sn.setImagen(song.getImagen());
		}
        
        if (song.getTitulo()!=null) {
			 sn.setTitulo(song.getTitulo());
		}
        
        if (song.getUrl()!=null) {
			 sn.setUrl(song.getUrl());
		}
        
        if (song.getTipo()!=null) {
			 sn.setTipo(song.getTipo());
		}
        
        if (song.getCreador()!=null) {
			 sn.setCreador(song.getCreador());
		}
        return repository.save(sn);
        
    }

    public Song update(Long id, Song song) {
    	
        if (song==null || id==null) {
        	throw new BadRequestException("Song and id shoud not be null");
		}
    	
        Song sn = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Song not found"));
        


        
        
        if (song.getImagen()!=null) {
			 sn.setImagen(song.getImagen());
		}
        
        if (song.getTitulo()!=null) {
			 sn.setTitulo(song.getTitulo());
		}
        
        if (song.getUrl()!=null) {
			 sn.setUrl(song.getUrl());
		}
        
        if (song.getTipo()!=null) {
			 sn.setTipo(song.getTipo());
		}
        

        return repository.save(sn);
    }

    public void delete(Long id) {
    	if (id==null) {
        	throw new BadRequestException("id shoud not be null");
		}
        repository.deleteById(id);
    }
}
