package com.example.spctn.Service;

import java.util.List;

import com.example.spctn.Entity.SavedSong;

public interface SavedSongService {




	SavedSong save(SavedSong guardado);

	    void delete(Long id);

	    List<SavedSong> findAll();
	
	
	
	
}
