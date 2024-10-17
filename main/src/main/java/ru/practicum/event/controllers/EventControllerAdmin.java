package ru.practicum.event.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.EventServiceImpl;
import ru.practicum.event.dto.event.UpdateEventAdminRequestDto;
import ru.practicum.event.dto.event.EventDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/events")
public class EventControllerAdmin {

	private final EventServiceImpl eventService;

	@Autowired
	public EventControllerAdmin(EventServiceImpl eventService) {
		this.eventService = eventService;
	}



	@GetMapping
	public List<EventDto> getEvents(@RequestParam(defaultValue = "0") int from,
									@RequestParam(defaultValue = "10") int size,
									@RequestParam(required = false) Long[] users,
									@RequestParam(required = false) String[] states,
									@RequestParam(required = false) Long[] categories,
									@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeStart,
									@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeEnd) {
		return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
	}

	@PatchMapping("/{eventId}")
	public EventDto updateEventByAdmin(@Valid @RequestBody UpdateEventAdminRequestDto request,
									   @PathVariable("eventId") Long eventId) {
		EventDto updatedEvent = eventService.updateEventByAdmin(request, eventId);
		log.info("Updated event by id of " + eventId + " with data from " + request.toString());
		return updatedEvent;
	}





}
