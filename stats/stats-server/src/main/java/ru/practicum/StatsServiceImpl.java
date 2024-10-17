package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

	private final StatsRepository statsRepository;

	@Autowired
	public StatsServiceImpl(StatsRepository statsRepository) {
		this.statsRepository = statsRepository;

	}

	@Override
	@Transactional
	public EndpointHitDto saveStats(EndpointHitDto dto) {
		statsRepository.save(StatsMapper.toEndpointHit(dto));
		return dto;
	}

	@Override
	public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
		List<ViewStats> stats = new ArrayList<>();
		if (start == null || end == null) {
			throw new ValidationException("Start or end cannot be null");
		}
		if (start.isAfter(end)) {
			throw new ValidationException("End cannot be before start");
		}
		if (!unique && uris == null) {
			stats = statsRepository.findStatsByStartAndEnd(start, end);
		}
		if (unique && uris == null) {
			stats = statsRepository.findStatsUniqueIp(start, end);
		}
		if (!unique && uris != null) {
			stats = statsRepository.findStatsWithUris(start, end, uris);
		}
		if (unique && uris != null) {
			stats = statsRepository.findStatsUniqueIpWithUris(start, end, uris);
		}
		return stats;
	}
}
