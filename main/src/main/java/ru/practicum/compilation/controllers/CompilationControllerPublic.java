package ru.practicum.compilation.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilation.CompilationService;
import ru.practicum.compilation.dto.CompilationDto;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compilations")
public class CompilationControllerPublic {

	private final CompilationService compilationService;

	@Autowired
	public CompilationControllerPublic(CompilationService compilationService) {
		this.compilationService = compilationService;
	}

	@GetMapping
	public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "0") int from,
												@RequestParam(defaultValue = "10") int size,
												@RequestParam(required = false) Boolean pinned) {
		if (pinned != null) {
			return compilationService.getCompilationsPinned(pinned, from, size);
		} else {
			return compilationService.getCompilations(from, size);
		}
	}

	@GetMapping("/{compId}")
	public CompilationDto getCompilationById(@PathVariable("compId") Long compId) {
		return compilationService.getCompilationById(compId);
	}

}
