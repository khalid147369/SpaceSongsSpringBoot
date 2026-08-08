package com.example.spctn.Service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Entity.Like;

public interface LikeService {

    Like save(Like like);

    void delete(Long id);
    
    public Like findLikeById(Long id);

	SongResponseDTO findLastLikedSong();

	Page<SongResponseDTO> findMostLikedSongs(Pageable pageable);

}
