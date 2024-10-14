package ru.practicum.category;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryRequestDto;

import java.util.List;

public interface CategoryService {

	CategoryDto create(NewCategoryRequestDto dto);


	void delete(Long categoryId);


	CategoryDto update(NewCategoryRequestDto dto, Long categoryId);

	List<CategoryDto> getCategories(int from, int size);

	CategoryDto getCategoryById(Long categoryId);
}
