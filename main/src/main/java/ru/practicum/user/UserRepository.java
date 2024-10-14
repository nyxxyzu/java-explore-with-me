package ru.practicum.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select u from User u " +
			       "order by u.id " +
	               "limit ?2 offset ?1")
	List<User> findUsers(int from, int size);

	@Query(value = "select u from User u " +
			       "where u.id in ?1 ")
	List<User> findUsersByIds(Long[] users);

}
