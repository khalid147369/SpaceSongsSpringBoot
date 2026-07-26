package com.example.spctn.Service.Impl;

import com.example.spctn.Dto.Request.CategoryRequestDTO;
import com.example.spctn.Dto.Response.CategoryResponseDTO;
import com.example.spctn.Dto.Response.CloudinaryResponse;
import com.example.spctn.Entity.Category;
import com.example.spctn.Exeption.BadRequestException;
import com.example.spctn.Mapper.CategoryMapper;
import com.example.spctn.Repository.CategoryRepository;
import com.example.spctn.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CloudinaryService cloudinaryService;
    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) throws IOException {
    	
    	CloudinaryResponse imageResponse = cloudinaryService.uploadFile(requestDTO.getImageFile(), "categorias");
        Category category = categoryMapper.toEntity(requestDTO);
        category.setImageUrl(imageResponse.getUrl());
        category.onUpdate();
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO) throws IOException {

    	if ((requestDTO.getDescription()==null || requestDTO.getDescription().isEmpty() || requestDTO.getDescription().isBlank()) && (requestDTO.getName()==null || requestDTO.getName().isEmpty() || requestDTO.getName().isBlank()) && requestDTO.getImageFile()==null ) {
    		throw new BadRequestException("one field at least must be filled");
    	}
    	
    	Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        
        if (requestDTO.getName()!=null && !requestDTO.getName().isEmpty() && !requestDTO.getName().isBlank() ) {
			category.setNombre(requestDTO.getName());
		}
        if (requestDTO.getDescription()!=null && !requestDTO.getDescription().isEmpty() && !requestDTO.getDescription().isBlank() ) {
			category.setDescripcion(requestDTO.getDescription());
		}
        if (requestDTO.getImageFile()!=null ) {
        	CloudinaryResponse imageResponse = cloudinaryService.uploadFile(requestDTO.getImageFile(), "categorias");
        	category.setImageUrl(imageResponse.getUrl());
		}
        
        category.onUpdate();
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
