package ru.practicum.event.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.enums.Sort;
import ru.practicum.event.EventServiceImpl;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.event.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventControllerPublic {

	private final EventServiceImpl eventService;
	private final StatsClient statsClient;

	@Autowired
	public EventControllerPublic(EventServiceImpl eventService, StatsClient statsClient) {
		this.eventService = eventService;
		this.statsClient = statsClient;
	}

	@GetMapping
	public List<EventShortDto> getPublicEvents(@RequestParam(required = false) @Size(min = 3, max = 500) String text,
											   @RequestParam(required = false) Long[] categories,
											   @RequestParam(required = false) Boolean paid,
											   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeStart,
											   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeEnd,
											   @RequestParam(defaultValue = "false") Boolean onlyAvailable,
											   @RequestParam(required = false) Sort sort,
											   @RequestParam(defaultValue = "0") int from,
											   @RequestParam(defaultValue = "10") int size,
											   HttpServletRequest request) {
		log.info(statsClient.saveStats(new EndpointHitDto("main", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now())).toString());
		return eventService.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
	}

	@GetMapping("/{id}")
	public EventDto getPublicEventById(@PathVariable("id") Long eventId, HttpServletRequest request) {

		statsClient.saveStats(new EndpointHitDto("main", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now()));
		return eventService.getPublicEventById(eventId);
	}
}
