package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCategoryRequestDto {

	@NotBlank
	@Size(max = 50)
	private String name;

}
