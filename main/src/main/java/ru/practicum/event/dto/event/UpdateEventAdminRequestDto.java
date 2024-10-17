package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.enums.StateActionAdmin;
import ru.practicum.event.dto.location.LocationDto;

import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequestDto {

	@Size(min = 20, max = 2000)
	private String annotation;
	private Long category;
	@Size(min = 20, max = 7000)
	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventDate;
	private LocationDto location;
	private Boolean paid;
	private Long participantLimit;
	private Boolean requestModeration;
	private StateActionAdmin stateAction;
	@Size(min = 3, max = 120)
	private String title;
}
