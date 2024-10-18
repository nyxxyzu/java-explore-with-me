package ru.practicum.event.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.EventServiceImpl;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventRequestDto;
import ru.practicum.event.dto.event.UpdateEventUserRequestDto;
import ru.practicum.event.dto.request.EventRequestStatusUpdateResultDto;
import ru.practicum.event.dto.request.EventUpdateStatusUpdateRequestDto;
import ru.practicum.event.dto.request.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@Slf4j
public class EventControllerPrivate {

	private final EventServiceImpl eventService;

	@Autowired
	public EventControllerPrivate(EventServiceImpl eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/events")
	@ResponseStatus(HttpStatus.CREATED)
	public EventDto create(@Valid @RequestBody NewEventRequestDto dto,
						   @PathVariable("userId") Long userId) {
		EventDto createdEvent = eventService.create(dto, userId);
		log.info("Created event {}", createdEvent.toString());
		return createdEvent;
	}

	@GetMapping("/events")
	public List<EventShortDto> getEventsByUserId(@PathVariable("userId") Long userId,
												 @RequestParam(defaultValue = "0") int from,
												 @RequestParam(defaultValue = "10") int size) {
		return eventService.getEventsByUserId(userId, from, size);
	}

	@GetMapping("/events/{eventId}")
	public EventDto getEventByUserAndEventId(@PathVariable("userId") Long userId,
											 @PathVariable("eventId") Long eventId) {
		return eventService.getEventByUserAndEventId(userId, eventId);
	}

	@PatchMapping("/events/{eventId}")
	public EventDto updateEventByUser(@Valid @RequestBody UpdateEventUserRequestDto request,
									  @PathVariable("userId") Long userId,
									  @PathVariable("eventId") Long eventId) {
		EventDto updatedEvent = eventService.updateEventByUser(request, userId, eventId);
		log.info("Updated event by id of " + eventId + " with data from " + request);
		return updatedEvent;
	}

	@GetMapping("/events/{eventId}/requests")
	public List<ParticipationRequestDto> getRequestsByEvent(@PathVariable("userId") Long userId,
															@PathVariable("eventId") Long eventId) {
		return eventService.getRequestsByEvent(userId, eventId);
	}

	@PatchMapping("/events/{eventId}/requests")
	public EventRequestStatusUpdateResultDto changeRequestStatus(@Valid @RequestBody EventUpdateStatusUpdateRequestDto dto,
																 @PathVariable("userId") Long userId,
																 @PathVariable("eventId") Long eventId) {
		return eventService.changeRequestStatus(dto, userId, eventId);
	}

	@PostMapping("/requests")
	@ResponseStatus(HttpStatus.CREATED)
	public ParticipationRequestDto createRequest(@PathVariable("userId") Long userId,
												 @RequestParam("eventId") Long eventId) {
		ParticipationRequestDto dto = eventService.createRequest(userId, eventId);
		log.info("Created request {} ", dto.toString());
		return dto;
	}

	@GetMapping("/requests")
	public List<ParticipationRequestDto> getRequestsByRequester(@PathVariable("userId") Long userId) {
		return eventService.getRequestsByRequester(userId);
	}

	@PatchMapping("/requests/{requestId}/cancel")
	public ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
												 @PathVariable("requestId") Long requestId) {
		log.info("Canceled request {}", requestId);
		return eventService.cancelRequest(userId, requestId);
	}
}
