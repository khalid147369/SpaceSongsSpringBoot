package com.example.spctn.Controller;


import com.example.spctn.Dto.Request.CategoryRequestDTO;
import com.example.spctn.Dto.Response.CategoryResponseDTO;
import com.example.spctn.Service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @ModelAttribute CategoryRequestDTO requestDTO) throws IOException {
        return new ResponseEntity<>(categoryService.create(requestDTO), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id, 
            @RequestBody CategoryRequestDTO requestDTO) throws IOException {
        return ResponseEntity.ok(categoryService.update(id, requestDTO));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
