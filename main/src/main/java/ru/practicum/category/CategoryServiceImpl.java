package ru.practicum.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.CategoryMapper;
import ru.practicum.category.dto.NewCategoryRequestDto;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	@Transactional
	public CategoryDto create(NewCategoryRequestDto dto) {
		Category category = CategoryMapper.toCategory(dto);
		return CategoryMapper.toCategoryDto(categoryRepository.save(category));
	}

	@Override
	@Transactional
	public void delete(Long categoryId) {
		if (categoryRepository.findById(categoryId).isEmpty()) {
			throw new NotFoundException("Category with id = " + categoryId + " was not found");
		}
		categoryRepository.deleteById(categoryId);
	}

	@Override
	@Transactional
	public CategoryDto update(NewCategoryRequestDto dto, Long categoryId) {
		Category oldCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category with id = " + categoryId + " was not found"));
		if (dto.getName() != null && !dto.getName().isBlank()) {
			oldCategory.setName(dto.getName());
		}
		return CategoryMapper.toCategoryDto(categoryRepository.save(oldCategory));
	}

	@Override
	public List<CategoryDto> getCategories(int from, int size) {
		return categoryRepository.findCategories(from, size).stream()
				.map(CategoryMapper::toCategoryDto)
				.toList();
	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category with id = " + categoryId + " was not found"));
		return CategoryMapper.toCategoryDto(category);
	}
}
