package ru.practicum.user;

import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

	UserDto create(NewUserRequestDto dto);

	void delete(Long userId);

	List<UserDto> getUsers(Long[] users, int from, int size);
}
