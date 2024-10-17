package ru.practicum.event.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class CommentDto {

	private Long id;
	private UserShortDto poster;
	private String text;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime posted;


}
