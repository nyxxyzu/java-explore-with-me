package ru.practicum.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(value = "select c from Category c " +
			"order by c.id " +
			"limit ?2 offset ?1")
	List<Category> findCategories(int from, int size);


}
