package ru.practicum.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.Category;
import ru.practicum.category.CategoryRepository;
import ru.practicum.dto.ViewStats;
import ru.practicum.enums.RequestStatus;
import ru.practicum.enums.Sort;
import ru.practicum.enums.StateAction;
import ru.practicum.enums.StateActionAdmin;
import ru.practicum.event.dto.event.UpdateEventAdminRequestDto;
import ru.practicum.event.dto.event.UpdateEventUserRequestDto;
import ru.practicum.event.dto.request.ParticipationRequestDto;
import ru.practicum.enums.State;
import ru.practicum.event.dto.event.EventDto;
import ru.practicum.event.dto.EventMapper;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventRequestDto;
import ru.practicum.event.dto.request.EventRequestStatusUpdateResultDto;
import ru.practicum.event.dto.request.EventUpdateStatusUpdateRequestDto;
import ru.practicum.exception.ConditionsNotMetException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;
import ru.practicum.StatsClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Transactional(readOnly = true)
@Service
@Slf4j
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final LocationRepository locationRepository;
	private final CategoryRepository categoryRepository;
	private final RequestRepository requestRepository;
	private final StatsClient statsClient;

	@Autowired
	public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
							LocationRepository locationRepository, CategoryRepository categoryRepository,
							RequestRepository requestRepository, StatsClient statsClient) {
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
		this.locationRepository = locationRepository;
		this.categoryRepository = categoryRepository;
		this.requestRepository = requestRepository;
		this.statsClient = statsClient;
	}

	@Override
	@Transactional
	public EventDto create(NewEventRequestDto dto, Long userId) {
		if (dto.getEventDate().isBefore(LocalDateTime.now().plusHours(2L))) {
			throw new ValidationException("Event date should be at least 2 hours after the current moment");
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		Location location = locationRepository.save(EventMapper.toLocation(dto.getLocation()));
		Category category = categoryRepository.findById(dto.getCategory())
				.orElseThrow(() -> new NotFoundException("Category not found"));
		Event event = EventMapper.toEvent(dto);
		if (dto.getPaid() == null) {
			event.setPaid(false);
		}
		if (dto.getRequestModeration() == null) {
			event.setRequestModeration(true);
		}
		if (dto.getParticipantLimit() == null) {
			event.setParticipantLimit(0L);
		}
		event.setInitiator(user);
		event.setLocation(location);
		event.setCategory(category);
		event.setCreatedOn(LocalDateTime.now());
		event.setState(State.PENDING);
		event.setConfirmedRequests(0L);
		event.setViews(0L);
		return EventMapper.toEventDto(eventRepository.save(event));
	}

	@Override
	public List<EventShortDto> getEventsByUserId(Long userId, int from, int size) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		return eventRepository.getEventByUserId(userId, from, size)
				.stream()
				.map(EventMapper::toEventShortDto)
				.toList();

	}

	@Override
	public EventDto getEventByUserAndEventId(Long userId, Long eventId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		Event event = eventRepository.getEventByUserAndEventId(userId, eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		return EventMapper.toEventDto(event);
	}

	@Override
	@Transactional
	public EventDto updateEventByUser(UpdateEventUserRequestDto request, Long userId, Long eventId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		Event oldEvent = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		if (oldEvent.getState() == State.PUBLISHED) {
			throw new ConditionsNotMetException("Cannot update published events");
		}
		if (request.getAnnotation() != null) {
			oldEvent.setAnnotation(request.getAnnotation());
		}
		if (request.getCategory() != null) {
			oldEvent.setCategory(categoryRepository.findById(request.getCategory()).orElseThrow(() -> new NotFoundException("Category not found")));
		}
		if (request.getEventDate() != null) {
			if (request.getEventDate().isBefore(LocalDateTime.now().plusHours(2L))) {
				throw new ValidationException("Event date should be at least 2 hours after the current moment");
			}
			oldEvent.setEventDate(request.getEventDate());
		}
		if (request.getLocation() != null) {
			Location location = locationRepository.save(EventMapper.toLocation(request.getLocation()));
			oldEvent.setLocation(location);
		}
		if (request.getPaid() != null) {
			oldEvent.setPaid(request.getPaid());
		}
		if (request.getParticipantLimit() != null) {
			oldEvent.setParticipantLimit(request.getParticipantLimit());
		}
		if (request.getRequestModeration() != null) {
			oldEvent.setRequestModeration(request.getRequestModeration());
		}
		if (request.getStateAction() != null) {
			if (request.getStateAction() == StateAction.SEND_TO_REVIEW) {
				oldEvent.setState(State.PENDING);
			}
			if (request.getStateAction() == StateAction.CANCEL_REVIEW) {
				oldEvent.setState(State.CANCELED);
			}
		}
		if (request.getTitle() != null) {
			oldEvent.setTitle(request.getTitle());
		}
		if (request.getDescription() != null) {
			oldEvent.setDescription(request.getDescription());
		}
		return EventMapper.toEventDto(oldEvent);
	}

	@Override
	public List<EventDto> getEvents(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart,
									LocalDateTime rangeEnd, int from, int size) {
		return eventRepository.getEvents(users, states, categories, rangeStart, rangeEnd, from, size)
				.stream()
				.map(EventMapper::toEventDto)
				.toList();
	}

	@Override
	@Transactional
	public EventDto updateEventByAdmin(UpdateEventAdminRequestDto request, Long eventId) {
		Event oldEvent = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		if (request.getAnnotation() != null) {
			oldEvent.setAnnotation(request.getAnnotation());
		}
		if (request.getCategory() != null) {
			oldEvent.setCategory(categoryRepository.findById(request.getCategory())
					.orElseThrow(() -> new NotFoundException("Category not found")));
		}
		if (request.getEventDate() != null) {
			if (request.getEventDate().isBefore(LocalDateTime.now().plusHours(2L))) {
				throw new ValidationException("Event date should be at least 2 hours after the current moment");
			}
			oldEvent.setEventDate(request.getEventDate());
		}
		if (request.getLocation() != null) {
			Location location = locationRepository.save(EventMapper.toLocation(request.getLocation()));
			oldEvent.setLocation(location);
		}
		if (request.getPaid() != null) {
			oldEvent.setPaid(request.getPaid());
		}
		if (request.getParticipantLimit() != null) {
			oldEvent.setParticipantLimit(request.getParticipantLimit());
		}
		if (request.getRequestModeration() != null) {
			oldEvent.setRequestModeration(request.getRequestModeration());
		}
		if (request.getStateAction() != null) {
			if (request.getStateAction() == StateActionAdmin.PUBLISH_EVENT) {
				if (oldEvent.getState() == State.PENDING) {
					oldEvent.setState(State.PUBLISHED);
					oldEvent.setPublishedOn(LocalDateTime.now());
				} else {
					throw new ConditionsNotMetException("Cannot publish an event that isn't pending");
				}
			}
			if (request.getStateAction() == StateActionAdmin.REJECT_EVENT) {
				if (!oldEvent.getState().equals(State.PUBLISHED)) {
					oldEvent.setState(State.CANCELED);
				} else {
					throw new ConditionsNotMetException("Cannot decline published event");
				}
			}
		}
		if (request.getTitle() != null) {
			oldEvent.setTitle(request.getTitle());
		}
		if (request.getDescription() != null) {
			oldEvent.setDescription(request.getDescription());
		}
		return EventMapper.toEventDto(oldEvent);

	}

	@Override
	public List<EventShortDto> getPublicEvents(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
											   LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, int from, int size) {
		if (text != null) {
			text = text.toLowerCase();
			text = "%" + text + "%";
		}
		List<Event> events = eventRepository.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size);

		if (rangeStart != null && rangeStart.isAfter(rangeEnd)) {
			throw new ValidationException("Range end cannot be before range start");
		}
		if (sort == Sort.EVENT_DATE) {
			return events.stream()
					.map(EventMapper::toEventShortDto)
					.sorted(Comparator.comparing(EventShortDto::getEventDate))
					.toList();
		}
		if (sort == Sort.VIEWS) {
			return events.stream()
					.map(EventMapper::toEventShortDto)
					.sorted(Comparator.comparing(EventShortDto::getViews))
					.toList();
		}
		return events.stream()
				.map(EventMapper::toEventShortDto)
				.toList();
	}

	@Transactional
	@Override
	public EventDto getPublicEventById(Long eventId) {
		Event event = eventRepository.getPublicEventById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		event.setViews(getViews(event.getPublishedOn().minusDays(14), event.getPublishedOn().plusDays(14), "/events/" + eventId));
		return EventMapper.toEventDto(event);
	}

	@Override
	@Transactional
	public ParticipationRequestDto createRequest(Long userId, Long eventId) {
		if (requestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
			throw new ConditionsNotMetException("This request already exists");
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event " + eventId + " not found"));
		if (event.getInitiator().getId().equals(userId)) {
			throw new ConditionsNotMetException("Cannot make a request for your own event");
		}
		if (!event.getState().equals(State.PUBLISHED)) {
			throw new ConditionsNotMetException("Cannot participate in an unpublished event");
		}
		if (event.getConfirmedRequests().equals(event.getParticipantLimit()) && event.getParticipantLimit() != 0) {
			throw new ConditionsNotMetException("Event has reached a participant limit");
		}
		ParticipationRequest request = new ParticipationRequest();
		request.setRequester(user);
		request.setEvent(event);
		request.setCreated(LocalDateTime.now());
		if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
			request.setStatus(RequestStatus.CONFIRMED);
			event.setConfirmedRequests(event.getConfirmedRequests() + 1);
		} else {
			request.setStatus(RequestStatus.PENDING);
		}
		return EventMapper.toParticipationDto(requestRepository.save(request));
	}

	@Override
	public List<ParticipationRequestDto> getRequestsByRequester(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		List<ParticipationRequest> requests = requestRepository.findByRequesterId(userId);
		return requests.stream()
				.map(EventMapper::toParticipationDto)
				.toList();

	}

	@Override
	@Transactional
	public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		ParticipationRequest request = requestRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("Request not found"));
		request.setStatus(RequestStatus.CANCELED);
		return EventMapper.toParticipationDto(requestRepository.save(request));
	}

	@Override
	public List<ParticipationRequestDto> getRequestsByEvent(Long userId, Long eventId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		List<ParticipationRequest> requests = requestRepository.findByEventId(eventId);
		return requests.stream()
				.map(EventMapper::toParticipationDto)
				.toList();
	}

	@Override
	@Transactional
	public EventRequestStatusUpdateResultDto changeRequestStatus(EventUpdateStatusUpdateRequestDto dto, Long userId, Long eventId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
			throw new ConditionsNotMetException("This event does not require request confirmation");
		}
		List<ParticipationRequest> requests = requestRepository.findByIdIn(dto.getRequestIds());
		List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
		List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
		if (event.getConfirmedRequests().equals(event.getParticipantLimit())) {
			throw new ConditionsNotMetException("Event has reached participation limit");
		}
		for (ParticipationRequest request : requests) {
			if (!request.getStatus().equals(RequestStatus.PENDING)) {
				throw new ConditionsNotMetException("You can only change status of pending requests");
			}
			if (dto.getStatus().equals(RequestStatus.CONFIRMED)) {
				if (event.getConfirmedRequests() < event.getParticipantLimit()) {
					request.setStatus(RequestStatus.CONFIRMED);
					event.setConfirmedRequests(event.getConfirmedRequests() + 1);
					confirmedRequests.add(EventMapper.toParticipationDto(request));
				} else {
					request.setStatus(RequestStatus.REJECTED);
					rejectedRequests.add(EventMapper.toParticipationDto(request));
				}
			} else {
				request.setStatus(RequestStatus.REJECTED);
				rejectedRequests.add(EventMapper.toParticipationDto(request));
			}
		}
		requestRepository.saveAll(requests);
		EventRequestStatusUpdateResultDto result = new EventRequestStatusUpdateResultDto();
		result.setConfirmedRequests(confirmedRequests);
		result.setRejectedRequests(rejectedRequests);
		return result;
	}

	private Long getViews(LocalDateTime start, LocalDateTime end, String uri) {
		String[] uris = {uri};
		List<ViewStats> stats = statsClient.getViewStats(start, end, uris, true).getBody();
		if (stats != null && !stats.isEmpty()) {
			return stats.getFirst().getHits();
		} else {
			return 0L;
		}
	}
}
