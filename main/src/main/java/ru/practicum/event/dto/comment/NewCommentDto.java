package ru.practicum.event.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCommentDto {

	@NotBlank
	@Size(min = 5, max = 1000)
	private String text;
}
