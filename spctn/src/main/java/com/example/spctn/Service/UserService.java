package com.example.spctn.Service;

import java.util.List;

import com.example.spctn.Entity.User;

public interface UserService {


	    List<User> findAll();

	    User findById(Long id);

	    User save(User user);

	    User update(User user);

	    void delete(Long id);
	
		 public User findByEmail(String email);
	    
	    User getAuthenticatedUser();
	
	
}
