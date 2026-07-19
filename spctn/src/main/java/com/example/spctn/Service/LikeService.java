package com.example.spctn.Service;



import com.example.spctn.Entity.Like;

public interface LikeService {

    Like save(Like like);

    void delete(Long id);
    
    public Like findLikeById(Long id);

}
