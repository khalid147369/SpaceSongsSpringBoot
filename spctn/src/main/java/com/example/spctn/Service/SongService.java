package com.example.spctn.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;

public interface SongService {

	public Page<Song> findAll(String title ,String tipo , Pageable pageable);
	
	public Song findById(Long id);
	
	public Song save(Song song);
	
	public Song update(Long id, Song song);
	
	public void delete(Long id);
	
	public Long getCount(Long id);
	
	
	public Long incrementarLikes(Long id);
	
	public Long decrementarLikes(Long id);
	
	public List<Like> getLikes(Long id);
	
	
}
