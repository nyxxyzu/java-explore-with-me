package ru.practicum.compilation.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilation.CompilationService;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/compilations")
public class CompilationControllerAdmin {

	private final CompilationService compilationService;

	@Autowired
	public CompilationControllerAdmin(CompilationService compilationService) {
		this.compilationService = compilationService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompilationDto create(@Valid @RequestBody NewCompilationDto dto) {
		CompilationDto createdCompilation = compilationService.create(dto);
		log.info("Created compilation {}", createdCompilation.toString());
		return createdCompilation;
	}

	@DeleteMapping("/{compId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("compId") Long compId) {
		log.info("Deleted compilation {}", compId);
		compilationService.delete(compId);
	}

	@PatchMapping("/{compId}")
	public CompilationDto update(@Valid @RequestBody UpdateCompilationRequestDto dto,
								 @PathVariable("compId") Long compId) {
		CompilationDto updatedComp = compilationService.update(dto, compId);
		log.info("Updated comp {} with data from {}", compId, dto.toString());
		return updatedComp;
	}


}
