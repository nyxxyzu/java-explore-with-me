package ru.practicum.event.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.enums.RequestStatus;

import java.util.List;

@Data
public class EventUpdateStatusUpdateRequestDto {

	@NotNull
	List<Long> requestIds;

	@NotNull
	RequestStatus status;


}
