package ru.practicum.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentMapper;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.enums.State;
import ru.practicum.event.model.Event;
import ru.practicum.event.repositories.EventRepository;
import ru.practicum.exception.ConditionsNotMetException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(EventRepository eventRepository, UserRepository userRepository,
							  CommentRepository commentRepository) {
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;

	}

	@Override
	@Transactional
	public CommentDto postComment(NewCommentDto dto, Long userId, Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		if (!event.getState().equals(State.PUBLISHED)) {
			throw new ConditionsNotMetException("Cannot leave a comment on an unpublished event");
		}
		Comment comment = CommentMapper.toComment(dto);
		comment.setPosted(LocalDateTime.now());
		comment.setPoster(user);
		event.getComments().add(comment);
		return CommentMapper.toCommentDto(commentRepository.save(comment));
	}

	@Override
	@Transactional
	public CommentDto editComment(NewCommentDto dto, Long userId, Long commentId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Comment not found"));
		if (!comment.getPoster().getId().equals(userId)) {
			throw new ConditionsNotMetException("You can only edit your own comments");
		}
		comment.setText(dto.getText());
		return CommentMapper.toCommentDto(comment);
	}

	@Override
	@Transactional
	public void deleteCommentUser(Long userId, Long eventId, Long commentId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Comment not found"));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		if (!comment.getPoster().getId().equals(userId)) {
			throw new ConditionsNotMetException("You can only delete your own comments");
		}
		event.getComments().remove(comment);
		commentRepository.deleteById(commentId);
	}

	@Override
	public List<CommentDto> getCommentsForUserEvent(Long userId, Long eventId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		if (!eventRepository.existsById(eventId)) {
			throw new NotFoundException("Event not found");
		}
		List<Comment> comments = commentRepository.findByUserIdAndEventId(userId, eventId);
		return comments.stream()
				.map(CommentMapper::toCommentDto)
				.toList();
	}

	@Override
	@Transactional
	public void deleteCommentAdmin(Long eventId, Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Comment not found"));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new NotFoundException("Event not found"));
		event.getComments().remove(comment);
		commentRepository.deleteById(commentId);
	}

	@Override
	public CommentDto getCommentById(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Comment not found"));
		return CommentMapper.toCommentDto(comment);
	}

	@Override
	public List<CommentDto> getCommentsByUser(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		return commentRepository.findByPosterId(userId).stream()
				.map(CommentMapper::toCommentDto)
				.toList();
	}

	@Override
	public List<CommentDto> getComments(LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
		Pageable page = PageRequest.of(from, size);
		return commentRepository.getComments(rangeStart, rangeEnd, page).stream()
				.map(CommentMapper::toCommentDto)
				.toList();
	}
}
