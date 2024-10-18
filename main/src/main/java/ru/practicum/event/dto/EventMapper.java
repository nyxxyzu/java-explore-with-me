package ru.practicum.event.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.category.dto.CategoryMapper;
import ru.practicum.comment.dto.CommentMapper;
import ru.practicum.event.model.ParticipationRequest;
import ru.practicum.event.dto.request.ParticipationRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventRequestDto;
import ru.practicum.event.dto.location.LocationDto;
import ru.practicum.user.dto.UserMapper;

@UtilityClass
public class EventMapper {

	public EventDto toEventDto(Event event) {
		EventDto dto = new EventDto();
		dto.setId(event.getId());
		dto.setEventDate(event.getEventDate());
		dto.setAnnotation(event.getAnnotation());
		dto.setCategory(event.getCategory() != null ? CategoryMapper.toCategoryDto(event.getCategory()) : null);
		dto.setDescription(event.getDescription());
		dto.setInitiator(event.getInitiator() != null ? UserMapper.toUserShortDto(event.getInitiator()) : null);
		dto.setPaid(event.getPaid());
		dto.setLocation(event.getLocation() != null ? EventMapper.toLocationDto(event.getLocation()) : null);
		dto.setConfirmedRequests(event.getConfirmedRequests());
		dto.setCreatedOn(event.getCreatedOn());
		dto.setParticipantLimit(event.getParticipantLimit());
		dto.setPublishedOn(event.getPublishedOn());
		dto.setRequestModeration(event.getRequestModeration());
		dto.setTitle(event.getTitle());
		dto.setViews(event.getViews());
		dto.setState(event.getState());
		dto.setComments(event.getComments() != null ? event.getComments().stream()
				.map(CommentMapper::toCommentDto)
				.toList() : null);
		return dto;
	}

	public Event toEvent(NewEventRequestDto dto) {
		Event event = new Event();
		event.setAnnotation(dto.getAnnotation());
		event.setEventDate(dto.getEventDate());
		event.setPaid(dto.getPaid());
		event.setTitle(dto.getTitle());
		event.setRequestModeration(dto.getRequestModeration());
		event.setParticipantLimit(dto.getParticipantLimit());
		event.setDescription(dto.getDescription());
		return event;
	}

	public EventShortDto toEventShortDto(Event event) {
		EventShortDto dto = new EventShortDto();
		dto.setAnnotation(event.getAnnotation());
		dto.setCategory(event.getCategory() != null ? CategoryMapper.toCategoryDto(event.getCategory()) : null);
		dto.setEventDate(event.getEventDate());
		dto.setId(event.getId());
		dto.setInitiator(event.getInitiator() != null ? UserMapper.toUserShortDto(event.getInitiator()) : null);
		dto.setPaid(event.getPaid());
		dto.setViews(event.getViews());
		dto.setConfirmedRequests(event.getConfirmedRequests());
		dto.setTitle(event.getTitle());
		return dto;
	}

	public Location toLocation(LocationDto dto) {
		Location location = new Location();
		location.setLon(dto.getLon());
		location.setLat(dto.getLat());
		return location;
	}

	public LocationDto toLocationDto(Location location) {
		LocationDto dto = new LocationDto();
		dto.setLat(location.getLat());
		dto.setLon(location.getLon());
		return dto;
	}

	public ParticipationRequestDto toParticipationDto(ParticipationRequest request) {
		ParticipationRequestDto dto = new ParticipationRequestDto();
		dto.setCreated(request.getCreated());
		dto.setStatus(request.getStatus());
		dto.setRequester(request.getRequester() != null ? request.getRequester().getId() : null);
		dto.setId(request.getId());
		dto.setEvent(request.getEvent() != null ? request.getEvent().getId() : null);
		return dto;
	}

}
