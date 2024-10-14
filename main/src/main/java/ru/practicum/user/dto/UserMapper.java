package ru.practicum.user.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.user.User;

@UtilityClass
public class UserMapper {

	public static User toUser(NewUserRequestDto dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		return user;
	}

	public static UserDto toUserDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		return dto;
	}

	public static UserShortDto toUserShortDto(User user) {
		UserShortDto dto = new UserShortDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		return dto;
	}
}
