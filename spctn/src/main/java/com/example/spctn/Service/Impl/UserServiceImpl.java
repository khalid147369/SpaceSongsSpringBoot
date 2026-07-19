package com.example.spctn.Service.Impl;

import com.example.spctn.Entity.Role;
import com.example.spctn.Entity.User;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;

import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Repository.UserRepository;
import com.example.spctn.Service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository,PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    @Override
    public User save(User user) {
    	if (user.getEmail()== null || user.getNombre() == null || user.getPassword() == null) {
			throw new BadRequestException("all required fields must be filled");
		}
    	boolean existe = repository.existsByEmail(user.getEmail());
    	if (existe) {
    		throw new DuplicateResourceException("user already exist");
		}
    	User us = new User();
    	us.setRole(Role.USER);
    	us.setEmail(user.getEmail());
    	us.setNombre(user.getNombre());
    	us.setPassword( passwordEncoder.encode(user.getPassword()));
    	if (user.getFotoPerfil()!=null) {
			us.setFotoPerfil(user.getFotoPerfil());
		}
        return repository.save(us);
    }

    @Override
    public User update(User user) {
    	
    	if (user==null) {
        	throw new BadRequestException("user shoud not be null");
		}
        User existente = getAuthenticatedUser();
        
        if (existente==null) {
        	throw new ResourceNotFoundException("user not found");
		}

        if (user.getEmail()== null && user.getNombre() == null && user.getPassword() == null && user.getFotoPerfil() == null) {
			throw new BadRequestException("all required fields must be filled");
		}
        if (user.getEmail()!=null) {
			existente.setEmail(user.getEmail());
		}
        if (user.getNombre()!=null) {
        	existente.setNombre(user.getNombre());
		}
        if (user.getFotoPerfil()!=null) {
        	existente.setFotoPerfil(user.getFotoPerfil());
		}
        if (user.getPassword()!=null) {
        	existente.setPassword(passwordEncoder.encode(user.getPassword()));
		}
        
        
        

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
    	if (id==null) {
        	throw new BadRequestException("id not found");
		}
        repository.deleteById(id);
    }
    
    @Override
    public User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
    
}