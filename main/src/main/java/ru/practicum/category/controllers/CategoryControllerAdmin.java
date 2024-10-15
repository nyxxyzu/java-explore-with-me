package ru.practicum.category.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.CategoryService;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryRequestDto;


@RestController
@Slf4j
@RequestMapping("/admin/categories")
public class CategoryControllerAdmin {

	private final CategoryService categoryService;

	@Autowired
	public CategoryControllerAdmin(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryDto create(@Valid @RequestBody NewCategoryRequestDto dto) {
		CategoryDto createdCategory = categoryService.create(dto);
		log.info("Created category {}", createdCategory.toString());
		return createdCategory;
	}

	@DeleteMapping("/{categoryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("categoryId") Long categoryId) {
		categoryService.delete(categoryId);
		log.info("Deleted category, id = " + categoryId);
	}

	@PatchMapping("/{categoryId}")
	public CategoryDto update(@Valid @RequestBody NewCategoryRequestDto dto,
							  @PathVariable("categoryId") Long categoryId) {
		CategoryDto updatedCategory = categoryService.update(dto, categoryId);
		log.info("Updated category by id of " + categoryId + " with data from " + dto.toString());
		return updatedCategory;
	}


}
