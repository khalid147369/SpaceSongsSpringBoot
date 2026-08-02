package com.example.spctn.Mapper;



import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.UserRequestDTO;
import com.example.spctn.Dto.Response.*;
import com.example.spctn.Entity.Role;
import com.example.spctn.Entity.User;

@Component
public class UserMapper {

    public UserResponseDTO toResponse(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setFotoPerfil(user.getFotoPerfil());
        dto.setDescreption(user.getDescription());
        dto.setCategoryName(user.getFavoriteCategory().getNombre());
        


        return dto;
    }

    public User toEntity(UserRequestDTO dto) {

        User user = new User();

        user.setRole(Role.USER);
        user.setNombre(dto.getNombre());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDescription(dto.getDescreption());

        return user;
    }
}
