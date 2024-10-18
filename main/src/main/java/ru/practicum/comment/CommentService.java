package ru.practicum.comment;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

	CommentDto postComment(NewCommentDto dto, Long eventId, Long userId);

	CommentDto editComment(NewCommentDto dto, Long userId, Long commentId);

	void deleteCommentUser(Long userId, Long eventId, Long commentId);

	List<CommentDto> getCommentsForUserEvent(Long userId, Long eventId);

	void deleteCommentAdmin(Long eventId, Long commentId);

	CommentDto getCommentById(Long commentId);

	List<CommentDto> getCommentsByUser(Long userId);

	List<CommentDto> getComments(LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}
