package com.example.spctn.Mapper;



import org.springframework.stereotype.Component;

import com.example.spctn.Dto.Request.UserRequestDTO;
import com.example.spctn.Dto.Response.*;
import com.example.spctn.Entity.Role;
import com.example.spctn.Entity.User;

@Component
public class UserByIdMapper {

    public UserByIdResponseDTO toResponse(User user) {

    	UserByIdResponseDTO dto = new UserByIdResponseDTO();

        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setFotoPerfil(user.getFotoPerfil());
        dto.setDescreption(user.getDescription());
        dto.setCategoryName(user.getFavoriteCategory().getNombre());
        


        return dto;
    }


}
