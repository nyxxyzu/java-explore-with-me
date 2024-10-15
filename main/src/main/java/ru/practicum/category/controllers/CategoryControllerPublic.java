package ru.practicum.category.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.CategoryService;
import ru.practicum.category.dto.CategoryDto;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/categories")
public class CategoryControllerPublic {

	private final CategoryService categoryService;

	@Autowired
	public CategoryControllerPublic(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from,
										   @RequestParam(defaultValue = "10") int size) {
		return categoryService.getCategories(from, size);
	}

	@GetMapping("/{categoryId}")
	public CategoryDto getCategoryById(@PathVariable("categoryId") Long categoryId) {
		return categoryService.getCategoryById(categoryId);
	}
}
