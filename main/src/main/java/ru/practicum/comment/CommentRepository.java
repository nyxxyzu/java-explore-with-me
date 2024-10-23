package ru.practicum.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "select c.* from comments as c " +
			       "left join event_comment as ec on c.id = ec.comment_id " +
			       "left join events as e on e.id = ec.event_id " +
	               "where e.initiator_id = ?1 and e.id = ?2 " +
			       "order by c.posted", nativeQuery = true)
	List<Comment> findByUserIdAndEventId(Long userId, Long eventId);

	List<Comment> findByPosterId(Long posterId);

	@Query(value = "select c from Comment c " +
			       "where cast(?1 as timestamp) is null or c.posted between ?1 and ?2 " +
	               "order by c.posted")
	List<Comment> getComments(LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable page);
}
