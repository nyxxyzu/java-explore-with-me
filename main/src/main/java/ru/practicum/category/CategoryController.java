package ru.practicum.category;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryRequestDto;

import java.util.List;

@RestController
@Slf4j
public class CategoryController {

	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/admin/categories")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryDto create(@Valid @RequestBody NewCategoryRequestDto dto) {
		CategoryDto createdCategory = categoryService.create(dto);
		log.info("Created category {}", createdCategory.toString());
		return createdCategory;
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("categoryId") Long categoryId) {
		categoryService.delete(categoryId);
		log.info("Deleted category, id = " + categoryId);
	}

	@PatchMapping("/admin/categories/{categoryId}")
	public CategoryDto update(@Valid @RequestBody NewCategoryRequestDto dto,
							  @PathVariable("categoryId") Long categoryId) {
		CategoryDto updatedCategory = categoryService.update(dto, categoryId);
		log.info("Updated category by id of " + categoryId + " with data from " + dto.toString());
		return updatedCategory;
	}

	@GetMapping("/categories")
	public List<CategoryDto> getCategories(@RequestParam(value = "from", defaultValue = "0") int from,
										   @RequestParam(value = "size", defaultValue = "10") int size) {
		return categoryService.getCategories(from, size);
	}

	@GetMapping("/categories/{categoryId}")
	public CategoryDto getCategoryById(@PathVariable("categoryId") Long categoryId) {
		return categoryService.getCategoryById(categoryId);
	}
}
