package ru.practicum.event.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.enums.RequestStatus;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {

	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created;
	private Long event;
	private Long requester;
	private RequestStatus status;
}
