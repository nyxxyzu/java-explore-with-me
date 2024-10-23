package ru.practicum.event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

	Optional<ParticipationRequest> findByRequesterIdAndEventId(Long requesterId, Long eventId);

	List<ParticipationRequest> findByRequesterId(Long requesterId);

	List<ParticipationRequest> findByEventId(Long eventId);

	List<ParticipationRequest> findByIdIn(List<Long> requestIds);
}
