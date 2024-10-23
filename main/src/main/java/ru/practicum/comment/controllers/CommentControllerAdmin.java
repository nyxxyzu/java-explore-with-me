package ru.practicum.comment.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/events")
public class CommentControllerAdmin {

	private final CommentService commentService;

	@Autowired
	public CommentControllerAdmin(CommentService commentService) {
		this.commentService = commentService;
	}

	@DeleteMapping("/{eventId}/comments/{commentId}")
	public void deleteCommentAdmin(@PathVariable("eventId") Long eventId,
								   @PathVariable("commentId") Long commentId) {
		log.info("Deleted comment {}, admin endpoint", commentId);
		commentService.deleteCommentAdmin(eventId, commentId);
	}

	@GetMapping("/comments/{commentId}")
	public CommentDto getCommentById(@PathVariable("commentId") Long commentId) {
		return commentService.getCommentById(commentId);
	}

	@GetMapping("/comments")
	public List<CommentDto> getComments(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeStart,
										@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeEnd,
										@RequestParam(defaultValue = "0") int from,
										@RequestParam(defaultValue = "10") int size) {
		return commentService.getComments(rangeStart, rangeEnd, from, size);
	}

	@GetMapping("/comments/users/{userId}")
	public List<CommentDto> getCommentsByUser(@PathVariable("userId") Long userId) {
		return commentService.getCommentsByUser(userId);
	}
}
