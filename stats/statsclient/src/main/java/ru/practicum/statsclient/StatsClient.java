package ru.practicum.statsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.Map;

public class StatsClient extends BaseClient {

	@Autowired
	public StatsClient(@Value("${statsserver.url}") String serverUrl, RestTemplateBuilder builder) {
		super(
				builder
						.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
						.requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
						.build()
		);
	}

	public ResponseEntity<Object> saveStats(EndpointHitDto dto) {
		return post("/hit", dto);
	}

	public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
		Map<String, Object> params = Map.of("start", start, "end", end, "unique", unique, "uris", uris);
		return get("/stats", params);
	}
}
