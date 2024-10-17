package ru.practicum.compilation.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.event.dto.EventMapper;
import ru.practicum.compilation.Compilation;

@UtilityClass
public class CompilationMapper {

	public Compilation toCompilation(NewCompilationDto dto) {
		Compilation compilation = new Compilation();
		compilation.setTitle(dto.getTitle());
		compilation.setPinned(dto.getPinned());
		return compilation;
	}

	public CompilationDto toCompilationDto(Compilation compilation) {
		CompilationDto dto = new CompilationDto();
		dto.setId(compilation.getId());
		dto.setEvents(compilation.getEvents() != null ? compilation.getEvents().stream()
				.map(EventMapper::toEventShortDto).toList() : null);
		dto.setPinned(compilation.getPinned());
		dto.setTitle(compilation.getTitle());
		return dto;
	}
}
