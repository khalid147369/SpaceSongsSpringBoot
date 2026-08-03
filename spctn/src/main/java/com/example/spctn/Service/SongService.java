package com.example.spctn.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.spctn.Dto.Request.SongRequestDTO;
import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Entity.Like;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;

public interface SongService {

	public Page<Song> findAll(String title ,Long category, Pageable pageable);
	
	public Song findById(Long id);
	
	public Song save(SongRequestDTO songDto) throws IOException;
	
	public SongResponseDTO update(Long id, SongRequestDTO song) throws IOException;
	
	public void delete(Long id) throws IOException;
	
	public Long getCount(Long id);
	
	
	public Long incrementarLikes(Long id);
	
	public Long decrementarLikes(Long id);
	
	public List<Like> getLikes(Long id);
	
	public Page<Song> getTrendingThisWeek(int limit);
	

	Page<Song> findWithFilters(String text, Long categoryId, Pageable pageable);
}
