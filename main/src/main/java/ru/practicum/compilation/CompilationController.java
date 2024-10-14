package ru.practicum.compilation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

@RestController
@Slf4j
public class CompilationController {

	private final CompilationService compilationService;

	@Autowired
	public CompilationController(CompilationService compilationService) {
		this.compilationService = compilationService;
	}

	@PostMapping("/admin/compilations")
	@ResponseStatus(HttpStatus.CREATED)
	public CompilationDto create(@Valid @RequestBody NewCompilationDto dto) {
		CompilationDto createdCompilation = compilationService.create(dto);
		log.info("Created compilation {}", createdCompilation.toString());
		return createdCompilation;
	}

	@DeleteMapping("/admin/compilations/{compId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("compId") Long compId) {
		log.info("Deleted compilation {}", compId);
		compilationService.delete(compId);
	}

	@PatchMapping("/admin/compilations/{compId}")
	public CompilationDto update(@Valid @RequestBody UpdateCompilationRequest dto,
								 @PathVariable("compId") Long compId) {
		CompilationDto updatedComp = compilationService.update(dto, compId);
		log.info("Updated comp {} with data from {}", compId, dto.toString());
		return updatedComp;
	}

	@GetMapping("/compilations")
	public List<CompilationDto> getCompilations(@RequestParam(value = "from", defaultValue = "0") int from,
												@RequestParam(value = "size", defaultValue = "10") int size,
												@RequestParam(value = "pinned", required = false) Boolean pinned) {
		return compilationService.getCompilations(pinned, from, size);
	}

	@GetMapping("/compilations/{compId}")
	public CompilationDto getCompilationById(@PathVariable("compId") Long compId) {
		return compilationService.getCompilationById(compId);
	}
}
