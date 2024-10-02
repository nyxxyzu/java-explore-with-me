package ru.practicum.statsservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

	@Query(value = " select new ru.practicum.dto.ViewStats(eh.app, eh.uri, count(eh.ip)) from EndpointHit eh " +
	       " where eh.timestamp between ?1 and ?2" +
	       " group by eh.app, eh.uri")
	List<ViewStats> findStatsByStartAndEnd(LocalDateTime start, LocalDateTime end);

	@Query(value = " select new ru.practicum.dto.ViewStats(eh.app, eh.uri, count(eh.ip)) from EndpointHit eh " +
			" where eh.timestamp between ?1 and ?2 and eh.uri in ?3" +
	        " group by eh.app, eh.uri")
	List<ViewStats> findStatsWithUris(LocalDateTime start, LocalDateTime end, String[] uris);

	@Query(value = " select new ru.practicum.dto.ViewStats(eh.app, eh.uri, count(distinct eh.ip)) from EndpointHit eh " +
			" where eh.timestamp between ?1 and ?2" +
			" group by eh.app, eh.uri")
	List<ViewStats> findStatsUniqueIp(LocalDateTime start, LocalDateTime end);

	@Query(value = " select new ru.practicum.dto.ViewStats(eh.app, eh.uri, count(distinct eh.ip)) from EndpointHit eh " +
			" where eh.timestamp between ?1 and ?2 and eh.uri in ?3" +
			" group by eh.app, eh.uri")
	List<ViewStats> findStatsUniqueIpWithUris(LocalDateTime start, LocalDateTime end, String[] uris);







}
