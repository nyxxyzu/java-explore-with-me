package ru.practicum.compilation;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {
	@Transactional
	CompilationDto create(NewCompilationDto dto);

	@Transactional
	void delete(Long compId);

	@Transactional
	CompilationDto update(UpdateCompilationRequest dto, Long compId);

	List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

	CompilationDto getCompilationById(Long compId);
}
