package ru.practicum.event;

import ru.practicum.enums.Sort;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventRequestDto;
import ru.practicum.event.dto.event.UpdateEventAdminRequest;
import ru.practicum.event.dto.event.UpdateEventUserRequest;
import ru.practicum.event.dto.request.EventUpdateStatusUpdateRequest;
import ru.practicum.event.dto.request.ParticipationRequestDto;
import ru.practicum.event.dto.request.EventRequestStatusUpdateResult;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
	EventDto create(NewEventRequestDto dto, Long userId);

	List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

	EventDto getEventByUserAndEventId(Long userId, Long eventId);

	EventDto updateEventByUser(UpdateEventUserRequest request, Long userId, Long eventId);

	List<EventDto> getEvents(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart,
							 LocalDateTime rangeEnd, int from, int size);

	EventDto updateEventByAdmin(UpdateEventAdminRequest request, Long eventId);

	List<EventShortDto> getPublicEvents(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
										LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, int from, int size);

	EventDto getPublicEventById(Long eventId);

	ParticipationRequestDto createRequest(Long userId, Long eventId);

	List<ParticipationRequestDto> getRequestsByRequester(Long userId);

	ParticipationRequestDto cancelRequest(Long userId, Long requestId);

	List<ParticipationRequestDto> getRequestsByEvent(Long userId, Long eventId);

	EventRequestStatusUpdateResult changeRequestStatus(EventUpdateStatusUpdateRequest dto, Long userId, Long eventId);
}
