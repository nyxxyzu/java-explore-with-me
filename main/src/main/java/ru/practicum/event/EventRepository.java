package ru.practicum.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

	@Query(value = "select e from Event e " +
			"where e.initiator.id = ?1 " +
			"order by e.id " +
			"limit ?3 offset ?2")
	List<Event> getEventByUserId(Long userId, int from, int size);

	@Query(value = "select e from Event e " +
			"where e.initiator.id = ?1 and e.id = ?2 ")
	Optional<Event> getEventByUserAndEventId(Long userId, Long eventId);

	@Query(value = "select e from Event e " +
			"where (?1 is null or e.initiator.id in ?1) " +
			"and (?2 is null or e.state in ?2) " +
			"and (?3 is null or e.category.id in ?3) " +
			"and (cast(?4 as timestamp) is null or e.eventDate between ?4 and ?5) " +
			"order by e.id " +
			"limit ?7 offset ?6")
	List<Event> getEvents(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart,
						  LocalDateTime rangeEnd, int from, int size);

	@Query(value = "select e from Event e " +
			"where e.state = 'PUBLISHED' " +
			"and (?1 is null or (lower(e.annotation) like ?1 or lower(e.description) like ?1))" +
			"and (?2 is null or e.category.id in ?2) " +
			"and (?3 is null or e.paid = ?3) " +
			"and (cast(?4 as timestamp) is null or e.eventDate between ?4 and ?5) " +
			"and (?6 = false or e.confirmedRequests < e.participantLimit or e.participantLimit = null) " +
			"order by e.id " +
			"limit ?8 offset ?7")
	List<Event> getPublicEvents(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
								   LocalDateTime rangeEnd, Boolean onlyAvailable, int from, int size);

	@Query(value = "select e from Event e " +
			"where e.state = 'PUBLISHED' " +
			"and e.id = ?1")
	Optional<Event> getPublicEventById(Long eventId);

	List<Event> findByIdIn(List<Long> eventIds);

	long countByCategoryId(long id);

}
