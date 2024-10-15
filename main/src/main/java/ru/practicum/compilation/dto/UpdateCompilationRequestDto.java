package ru.practicum.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UpdateCompilationRequestDto {

	private List<Long> events;
	private Boolean pinned;
	@Size(max = 50)
	private String title;
}
