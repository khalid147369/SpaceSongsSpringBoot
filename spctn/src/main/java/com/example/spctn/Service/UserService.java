package com.example.spctn.Service;


import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.spctn.Dto.Request.UserRequestDTO;
import com.example.spctn.Dto.Request.UserUpdateRequestDTO;
import com.example.spctn.Dto.Response.UserResponseDTO;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;

public interface UserService {


	    List<User> findAll();

	    User findById();



	    void delete(Long id);
	
		 public User findByEmail(String email);
	    
	    User getAuthenticatedUser();

		Page<Song> lastSongPlayed(Pageable pageable);

		UserResponseDTO save(UserRequestDTO user) throws IOException;

		User update(UserUpdateRequestDTO user) throws IOException;

		
	
	
}
