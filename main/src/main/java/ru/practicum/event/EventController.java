package ru.practicum.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.enums.Sort;
import ru.practicum.event.dto.event.UpdateEventAdminRequest;
import ru.practicum.event.dto.event.UpdateEventUserRequest;
import ru.practicum.event.dto.request.ParticipationRequestDto;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventRequestDto;
import ru.practicum.event.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.event.dto.request.EventUpdateStatusUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class EventController {

	private final EventServiceImpl eventService;
	private final StatsClient statsClient;

	@Autowired
	public EventController(EventServiceImpl eventService, StatsClient statsClient) {
		this.eventService = eventService;
		this.statsClient = statsClient;
	}

	@PostMapping("/users/{userId}/events")
	@ResponseStatus(HttpStatus.CREATED)
	public EventDto create(@Valid @RequestBody NewEventRequestDto dto,
						   @PathVariable("userId") Long userId) {
		EventDto createdEvent = eventService.create(dto, userId);
		log.info("Created event {}", createdEvent.toString());
		return createdEvent;
	}

	@GetMapping("/users/{userId}/events")
	public List<EventShortDto> getEventsByUserId(@PathVariable("userId") Long userId,
												 @RequestParam(value = "from", defaultValue = "0") int from,
												 @RequestParam(value = "size", defaultValue = "10") int size) {
		return eventService.getEventsByUserId(userId, from, size);
	}

	@GetMapping("/users/{userId}/events/{eventId}")
	public EventDto getEventByUserAndEventId(@PathVariable("userId") Long userId,
											 @PathVariable("eventId") Long eventId) {
		return eventService.getEventByUserAndEventId(userId, eventId);
	}

	@PatchMapping("users/{userId}/events/{eventId}")
	public EventDto updateEventByUser(@Valid @RequestBody UpdateEventUserRequest request,
									  @PathVariable("userId") Long userId,
									  @PathVariable("eventId") Long eventId) {
		EventDto updatedEvent = eventService.updateEventByUser(request, userId, eventId);
		log.info("Updated event by id of " + eventId + " with data from " + request.toString());
		return updatedEvent;
	}

	@GetMapping("/admin/events")
	public List<EventDto> getEvents(@RequestParam(value = "from", defaultValue = "0") int from,
									@RequestParam(value = "size", defaultValue = "10") int size,
									@RequestParam(value = "users", required = false) Long[] users,
									@RequestParam(value = "states", required = false) String[] states,
									@RequestParam(value = "categories", required = false) Long[] categories,
									@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "rangeStart", required = false) LocalDateTime rangeStart,
									@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "rangeEnd", required = false) LocalDateTime rangeEnd) {
		return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
	}

	@PatchMapping("/admin/events/{eventId}")
	public EventDto updateEventByAdmin(@Valid @RequestBody UpdateEventAdminRequest request,
									   @PathVariable("eventId") Long eventId) {
		EventDto updatedEvent = eventService.updateEventByAdmin(request, eventId);
		log.info("Updated event by id of " + eventId + " with data from " + request.toString());
		return updatedEvent;
	}

	@GetMapping("/events")
	public List<EventShortDto> getPublicEvents(@RequestParam(value = "text", required = false) String text,
											   @RequestParam(value = "categories", required = false) Long[] categories,
											   @RequestParam(value = "paid", required = false) Boolean paid,
											   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "rangeStart", required = false) LocalDateTime rangeStart,
											   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "rangeEnd", required = false) LocalDateTime rangeEnd,
											   @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
											   @RequestParam(value = "sort", required = false) Sort sort,
											   @RequestParam(value = "from", defaultValue = "0") int from,
											   @RequestParam(value = "size", defaultValue = "10") int size,
											   HttpServletRequest request) {
		log.info(statsClient.saveStats(new EndpointHitDto("main", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now())).toString());
		return eventService.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
	}

	@GetMapping("/events/{id}")
	public EventDto getPublicEventById(@PathVariable("id") Long eventId, HttpServletRequest request) {

		statsClient.saveStats(new EndpointHitDto("main", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now()));
		return eventService.getPublicEventById(eventId);
	}

	@PostMapping("/users/{userId}/requests")
	@ResponseStatus(HttpStatus.CREATED)
	public ParticipationRequestDto createRequest(@PathVariable("userId") Long userId,
												 @RequestParam("eventId") Long eventId) {
		ParticipationRequestDto dto = eventService.createRequest(userId, eventId);
		log.info("Created request {} ", dto.toString());
		return dto;
	}

	@GetMapping("/users/{userId}/requests")
	public List<ParticipationRequestDto> getRequestsByRequester(@PathVariable("userId") Long userId) {
		return eventService.getRequestsByRequester(userId);
	}

	@PatchMapping("/users/{userId}/requests/{requestId}/cancel")
	public ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
												 @PathVariable("requestId") Long requestId) {
		log.info("Canceled request {}", requestId);
		return eventService.cancelRequest(userId, requestId);
	}

	@GetMapping("/users/{userId}/events/{eventId}/requests")
	public List<ParticipationRequestDto> getRequestsByEvent(@PathVariable("userId") Long userId,
															@PathVariable("eventId") Long eventId) {
		return eventService.getRequestsByEvent(userId, eventId);
	}

	@PatchMapping("/users/{userId}/events/{eventId}/requests")
	public EventRequestStatusUpdateResult changeRequestStatus(@Valid @RequestBody EventUpdateStatusUpdateRequest dto,
															  @PathVariable("userId") Long userId,
															  @PathVariable("eventId") Long eventId) {
		return eventService.changeRequestStatus(dto, userId, eventId);
	}
}
