package ru.practicum.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EndpointHitDto {

	@NotBlank
	private String app;
	@NotBlank
	private String uri;
	@NotBlank
	private String ip;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;

}
