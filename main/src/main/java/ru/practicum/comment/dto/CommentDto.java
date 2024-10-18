package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

	private Long id;
	private UserShortDto poster;
	private String text;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime posted;


}
