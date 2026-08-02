package com.example.spctn.Service.Impl;

import com.example.spctn.Dto.Request.UserRequestDTO;
import com.example.spctn.Dto.Request.UserUpdateRequestDTO;
import com.example.spctn.Dto.Response.CloudinaryResponse;
import com.example.spctn.Dto.Response.SongResponseDTO;
import com.example.spctn.Dto.Response.UserResponseDTO;
import com.example.spctn.Entity.Category;
import com.example.spctn.Entity.Role;
import com.example.spctn.Entity.Song;
import com.example.spctn.Entity.User;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Exeption.DuplicateResourceException;

import com.example.spctn.Exeption.ResourceNotFoundException;
import com.example.spctn.Mapper.UserMapper;
import com.example.spctn.Repository.CategoryRepository;
import com.example.spctn.Repository.ListenRepository;
import com.example.spctn.Repository.SongRepository;
import com.example.spctn.Repository.UserRepository;
import com.example.spctn.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ListenRepository listenRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository repository,PasswordEncoder passwordEncoder,CategoryRepository categoryRepository,ListenRepository listenRepository,UserMapper mapper,CloudinaryService cloudinaryService) {
        this.repository = repository;
		this.listenRepository = listenRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Song> lastSongPlayed(Pageable pageable) {
    	
    	User user = getAuthenticatedUser();
    	
    	
        return listenRepository.findByUserId(user.getId(),pageable);
    }
    
    @Override
    public User findById() {
        return getAuthenticatedUser();
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    @Override
    public UserResponseDTO save(UserRequestDTO user) throws IOException {
    	
		
    	
    	
		
    	
    	if (user.getEmail()== null || user.getNombre() == null || user.getPassword() == null) {
			throw new BadRequestException("all required fields must be filled");
		}
    	boolean existe = repository.existsByEmail(user.getEmail());
    	if (existe) {
    		throw new DuplicateResourceException("user already exist");
		}
    	User us = mapper.toEntity(user);
    	

    	us.setPassword( passwordEncoder.encode(user.getPassword()));
    	if (user.getFotoPerfil()!=null) {
    		CloudinaryResponse imageResponse = cloudinaryService.uploadFile(user.getFotoPerfil(), "avatars");
			us.setFotoPerfil(imageResponse.getUrl());
			us.setFotoPerfilPublicId(imageResponse.getPublicId());
		}
    	
    	
        return mapper.toResponse(repository.save(us));
    }

    @Override
    public User update(UserUpdateRequestDTO user) throws IOException {
    	

    
    	
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
        	if (existente.getFotoPerfilPublicId() != null) {
        	    cloudinaryService.deleteFile(existente.getFotoPerfilPublicId(),"image");
        	}
        	CloudinaryResponse imageResponse = cloudinaryService.uploadFile(user.getFotoPerfil(), "avatars");
        	existente.setFotoPerfil(imageResponse.getUrl());
        	existente.setFotoPerfilPublicId(imageResponse.getPublicId());
		}
        if (user.getPassword()!=null) {
        	existente.setPassword(passwordEncoder.encode(user.getPassword()));
		}
        
        if (user.getDescreption()!=null) {
        	existente.setDescription(user.getDescreption());
		}
        
        if (user.getCategory()!=null) {
        	Category category = categoryRepository.findById(user.getCategory()).orElseThrow(()-> new ResourceNotFoundException("Category not found")); 
        	existente.setFavoriteCategory(category);
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