package ru.practicum.category.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.category.Category;

@UtilityClass
public class CategoryMapper {

	public static Category toCategory(NewCategoryRequestDto dto) {
		Category category = new Category();
		category.setName(dto.getName());
		return category;
	}

	public static CategoryDto toCategoryDto(Category category) {
		CategoryDto dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setName(category.getName());
		return dto;
	}
}
