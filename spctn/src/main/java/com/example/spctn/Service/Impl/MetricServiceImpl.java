package com.example.spctn.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spctn.Dto.Response.DashboardMetricsDTO;
import com.example.spctn.Repository.CategoryRepository;
import com.example.spctn.Repository.CommentRepository;
import com.example.spctn.Repository.LikeRepository;
import com.example.spctn.Repository.ListenRepository;
import com.example.spctn.Repository.SavedSongRepository;
import com.example.spctn.Repository.SongRepository;
import com.example.spctn.Repository.UserRepository;

@Service
public class MetricServiceImpl {
	
	@Autowired
    private UserServiceImpl userServiceImpl;

	@Autowired
    private SongRepository songRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private ListenRepository listenRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private SavedSongRepository savedSongRepository;

    // Métricas globales del Dashboard
    public DashboardMetricsDTO getDashboardMetrics() {
        return new DashboardMetricsDTO(
            songRepository.count(),
            userRepository.count(),
            listenRepository.count(),
            likeRepository.count(),
            commentRepository.count()          
        );
    }

    // --- CONTEOS ESPECÍFICOS DE CANCIONES ---
    public long countTotalSongs() {
        return songRepository.count();
    }
    
    public long countTotalLikesBySong(Long songId) {
        return likeRepository.countBySongId(songId);
    }
    
    public long countTotalPlaysBySong(Long songId) {
        return listenRepository.countBySongId(songId);
    }
    
    public long countTotalCommentsBySong(Long songId) {
        return commentRepository.countBySongId(songId);
    }
    
    public long countTotalSavedSongsBySong(Long songId) {
        return savedSongRepository.countBySongId(songId);
    }

    public long countSongsByCategory(Long categoryId) {
        return songRepository.countByCategoryId(categoryId);
    }

    // --- CONTEOS ESPECÍFICOS DE USUARIOS ---
    public long countTotalUsers() {
        return userRepository.count();
    }
    
    public long countTotalLikesByUser() {
    	Long userId = userServiceImpl.getAuthenticatedUser().getId();
        return likeRepository.countByUserId(userId);
    }
    
    public long countTotalSavedSongByUser() {
    	Long userId = userServiceImpl.getAuthenticatedUser().getId();
        return savedSongRepository.countByUserId(userId);
    }
    
    public long countTotalCommentsByUser() {
    	Long userId = userServiceImpl.getAuthenticatedUser().getId();
        return commentRepository.countByUserId(userId);
    }


}