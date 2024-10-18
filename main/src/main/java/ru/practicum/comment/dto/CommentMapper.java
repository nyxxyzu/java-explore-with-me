package ru.practicum.comment.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.comment.Comment;
import ru.practicum.user.dto.UserMapper;

@UtilityClass
public class CommentMapper {

	public Comment toComment(NewCommentDto dto) {
		return Comment.builder()
				.text(dto.getText())
				.build();
	}

	public CommentDto toCommentDto(Comment comment) {
		return CommentDto.builder()
						.id(comment.getId())
						.text(comment.getText())
						.posted(comment.getPosted())
						.poster(comment.getPoster() != null ? UserMapper.toUserShortDto(comment.getPoster()) : null)
						.build();
	}
}
