package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class EventShortDto {

	private Long id;
	private String annotation;
	private CategoryDto category;
	private Long confirmedRequests;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventDate;
	private UserShortDto initiator;
	private Boolean paid;
	private String title;
	private Long views;
}
