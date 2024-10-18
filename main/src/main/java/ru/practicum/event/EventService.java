package ru.practicum.event;

import ru.practicum.enums.Sort;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventRequestDto;
import ru.practicum.event.dto.event.UpdateEventAdminRequestDto;
import ru.practicum.event.dto.event.UpdateEventUserRequestDto;
import ru.practicum.event.dto.request.EventUpdateStatusUpdateRequestDto;
import ru.practicum.event.dto.request.ParticipationRequestDto;
import ru.practicum.event.dto.request.EventRequestStatusUpdateResultDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
	EventDto create(NewEventRequestDto dto, Long userId);

	List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

	EventDto getEventByUserAndEventId(Long userId, Long eventId);

	EventDto updateEventByUser(UpdateEventUserRequestDto request, Long userId, Long eventId);

	List<EventDto> getEvents(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart,
							 LocalDateTime rangeEnd, int from, int size);

	EventDto updateEventByAdmin(UpdateEventAdminRequestDto request, Long eventId);

	List<EventShortDto> getPublicEvents(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
										LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, int from, int size);

	EventDto getPublicEventById(Long eventId);

	ParticipationRequestDto createRequest(Long userId, Long eventId);

	List<ParticipationRequestDto> getRequestsByRequester(Long userId);

	ParticipationRequestDto cancelRequest(Long userId, Long requestId);

	List<ParticipationRequestDto> getRequestsByEvent(Long userId, Long eventId);

	EventRequestStatusUpdateResultDto changeRequestStatus(EventUpdateStatusUpdateRequestDto dto, Long userId, Long eventId);

}
