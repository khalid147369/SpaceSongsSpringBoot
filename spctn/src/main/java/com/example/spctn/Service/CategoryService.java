package com.example.spctn.Service;


import java.io.IOException;
import java.util.List;



import com.example.spctn.Dto.Request.CategoryRequestDTO;
import com.example.spctn.Dto.Response.CategoryResponseDTO;

public interface CategoryService {
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO create(CategoryRequestDTO requestDTO) throws IOException;
    CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO)throws IOException;
    void delete(Long id);
}