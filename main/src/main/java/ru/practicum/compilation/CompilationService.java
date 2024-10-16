package ru.practicum.compilation;

import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {

	CompilationDto create(NewCompilationDto dto);

	void delete(Long compId);

	CompilationDto update(UpdateCompilationRequestDto dto, Long compId);

	List<CompilationDto> getCompilations(int from, int size);

	List<CompilationDto> getCompilationsPinned(Boolean pinned, int from, int size);

	CompilationDto getCompilationById(Long compId);
}
