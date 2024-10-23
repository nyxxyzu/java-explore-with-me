package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.event.dto.location.LocationDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDto {

	private Long id;
	private String annotation;
	private CategoryDto category;
	private Long confirmedRequests;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventDate;
	private UserShortDto initiator;
	private LocationDto location;
	private Boolean paid;
	private Long participantLimit;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime publishedOn;
	private Boolean requestModeration;
	private String title;
	private Long views;
	private State state;
	private List<CommentDto> comments;
}
