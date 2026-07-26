package com.example.spctn.Mapper;


import com.example.spctn.Dto.Request.CategoryRequestDTO;
import com.example.spctn.Dto.Response.CategoryResponseDTO;
import com.example.spctn.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setNombre(dto.getName());
        category.setDescripcion(dto.getDescription());
        return category;
    }

    public CategoryResponseDTO toResponse(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setNombre(category.getNombre());
        dto.setDescripcion(category.getDescripcion());
        dto.setImageUrl(category.getImageUrl());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}
