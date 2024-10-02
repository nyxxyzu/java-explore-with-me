package ru.practicum.statsservice;

import lombok.NoArgsConstructor;
import ru.practicum.dto.EndpointHitDto;

@NoArgsConstructor
public class StatsMapper {

	public static EndpointHit toEndpointHit(EndpointHitDto dto) {
		EndpointHit endpointHit = new EndpointHit();
		endpointHit.setApp(dto.getApp());
		endpointHit.setIp(dto.getIp());
		endpointHit.setUri(dto.getUri());
		endpointHit.setTimestamp(dto.getTimestamp());
		return endpointHit;
	}
}
