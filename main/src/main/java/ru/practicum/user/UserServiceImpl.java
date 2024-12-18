package ru.practicum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDto create(NewUserRequestDto dto) {
		User user = UserMapper.toUser(dto);
		return UserMapper.toUserDto(userRepository.save(user));
	}

	@Override
	@Transactional
	public void delete(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User with id = " + userId + " was not found");
		}
		userRepository.deleteById(userId);
	}

	@Override
	public List<UserDto> getUsers(Long[] users, int from, int size) {
		Pageable page = PageRequest.of(from, size);
		if (users != null) {
			return userRepository.findByIdIn(users)
					.stream()
					.map(UserMapper::toUserDto)
					.toList();
		} else {
			return userRepository.findAll(page)
					.stream()
					.map(UserMapper::toUserDto)
					.toList();
			}
	}
}
