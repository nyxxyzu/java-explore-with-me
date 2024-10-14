package ru.practicum.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

	@Query(value = "select c from Compilation c " +
	               "where ?1 is null or c.pinned = ?1 " +
	               "order by c.id " +
	               "limit ?3 offset ?2")
	List<Compilation> findByPinned(Boolean pinned, int from, int size);
}
