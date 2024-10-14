package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.event.dto.location.LocationDto;


import java.time.LocalDateTime;

@Data
public class NewEventRequestDto {

	@NotBlank
	@Size(min = 20, max = 2000)
	private String annotation;
	@NotNull
	private Long category;
	@NotBlank
	@Size(min = 20)
	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull
	private LocalDateTime eventDate;
	@NotNull
	private LocationDto location;
	private Boolean paid;
	@PositiveOrZero
	private Long participantLimit;
	private Boolean requestModeration;
	@NotEmpty
	@Size(min = 3, max = 120)
	private String title;
}
