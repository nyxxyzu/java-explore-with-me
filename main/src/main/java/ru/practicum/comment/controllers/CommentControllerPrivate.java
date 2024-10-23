package ru.practicum.comment.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@Slf4j
public class CommentControllerPrivate {

	private final CommentService commentService;

	@Autowired
	public CommentControllerPrivate(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/events/{eventId}/comments")
	public CommentDto postComment(@Valid @RequestBody NewCommentDto dto,
								  @PathVariable("userId") Long userId,
								  @PathVariable("eventId") Long eventId) {
		CommentDto comment = commentService.postComment(dto, userId, eventId);
		log.info("Left a comment {}", comment);
		return comment;
	}

	@PatchMapping("/comments/{commentId}")
	public CommentDto editComment(@Valid @RequestBody NewCommentDto dto,
								  @PathVariable("userId") Long userId,
								  @PathVariable("commentId") Long commentId) {
		CommentDto comment = commentService.editComment(dto, userId, commentId);
		log.info("Edited a comment {} with data from {}", commentId, dto);
		return comment;
	}

	@DeleteMapping("/events/{eventId}/comments/{commentId}")
	public void deleteCommentUser(@PathVariable("userId") Long userId,
								  @PathVariable("eventId") Long eventId,
								  @PathVariable("commentId") Long commentId) {
		log.info("Deleted comment {}", commentId);
		commentService.deleteCommentUser(userId, eventId, commentId);
	}

	@GetMapping("/events/{eventId}/comments")
	public List<CommentDto> getCommentsForUserEvent(@PathVariable("userId") Long userId,
													@PathVariable("eventId") Long eventId) {
		return commentService.getCommentsForUserEvent(userId, eventId);
	}
}
