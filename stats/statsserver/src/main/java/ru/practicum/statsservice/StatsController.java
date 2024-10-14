package ru.practicum.statsservice;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class StatsController {

	private final StatsService statsService;

	@Autowired
	public StatsController(StatsService statsService) {
		this.statsService = statsService;
	}

	@PostMapping("/hit")
	public EndpointHitDto saveStats(@Valid @RequestBody EndpointHitDto dto) {
		log.info("Saved stats {}", dto.toString());
		return statsService.saveStats(dto);
	}

	@GetMapping("/stats")
	public List<ViewStats> getStats(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam("start")  LocalDateTime start,
									@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam("end") LocalDateTime end,
									@RequestParam(value = "unique", defaultValue = "false") Boolean unique,
									@RequestParam(value = "uris", required = false) String[] uris) {
		return statsService.getStats(start, end, uris, unique);
	}
}
