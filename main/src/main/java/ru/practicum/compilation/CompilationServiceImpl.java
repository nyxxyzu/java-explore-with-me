package ru.practicum.compilation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationMapper;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.repositories.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CompilationServiceImpl implements CompilationService {

	private final CompilationRepository compilationRepository;
	private final EventRepository eventRepository;

	@Autowired
	public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
		this.compilationRepository = compilationRepository;
		this.eventRepository = eventRepository;
	}

	@Override
	@Transactional
	public CompilationDto create(NewCompilationDto dto) {
		Compilation compilation = CompilationMapper.toCompilation(dto);
		List<Event> events = eventRepository.findByIdIn(dto.getEvents());
		compilation.setEvents(events);
		if (dto.getPinned() == null) {
			compilation.setPinned(false);
		}
		return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
	}

	@Override
	@Transactional
	public void delete(Long compId) {
		if (!compilationRepository.existsById(compId)) {
			throw new NotFoundException("Compilation not found");
		}
		compilationRepository.deleteById(compId);
	}

	@Override
	@Transactional
	public CompilationDto update(UpdateCompilationRequestDto dto, Long compId) {
		Compilation compilation = compilationRepository.findById(compId)
				.orElseThrow(() -> new NotFoundException("Compilation not found"));
		if (dto.getPinned() != null) {
			compilation.setPinned(dto.getPinned());
		}
		if (dto.getTitle() != null) {
			compilation.setTitle(dto.getTitle());
		}
		if (dto.getEvents() != null) {
			List<Event> events = eventRepository.findByIdIn(dto.getEvents());
			compilation.setEvents(events);
		}
		return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
	}

	@Override
	public List<CompilationDto> getCompilations(int from, int size) {
		Pageable page = PageRequest.of(from, size);
		return compilationRepository.findAll(page).stream()
				.map(CompilationMapper::toCompilationDto)
				.toList();
		}

	@Override
	public List<CompilationDto> getCompilationsPinned(Boolean pinned, int from, int size) {
		Pageable page = PageRequest.of(from, size);
		return compilationRepository.findByPinnedIs(pinned, page).stream()
				.map(CompilationMapper::toCompilationDto)
				.toList();
	}

	@Override
	public CompilationDto getCompilationById(Long compId) {
		Compilation compilation = compilationRepository.findById(compId)
				.orElseThrow(() -> new NotFoundException("Compilation not found"));
		return CompilationMapper.toCompilationDto(compilation);
	}
}
