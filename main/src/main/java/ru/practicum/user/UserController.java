package ru.practicum.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto create(@Valid @RequestBody NewUserRequestDto dto) {
		UserDto createdUser = userService.create(dto);
		log.info("Created user {}", createdUser.toString());
		return createdUser;
	}

	@GetMapping
	public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) Long[] users,
								  @RequestParam(defaultValue = "0") int from,
								  @RequestParam(defaultValue = "10") int size) {
		return userService.getUsers(users, from, size);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("userId") Long userId) {
		userService.delete(userId);
		log.info("Deleted user, id = " + userId);
	}

}
