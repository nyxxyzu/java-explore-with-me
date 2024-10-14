package ru.practicum;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

	EndpointHitDto saveStats(EndpointHitDto dto);

	List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
