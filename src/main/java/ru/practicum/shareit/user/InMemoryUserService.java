package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryUserService implements UserService {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setId(idCounter.getAndIncrement());
        users.put(user.getId(), user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existing = users.get(userId);
        if (existing == null) return null;
        existing.setName(userDto.getName());
        existing.setEmail(userDto.getEmail());
        return UserMapper.toUserDto(existing);
    }

    @Override
    public void deleteUser(Long userId) {
        users.remove(userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.toUserDto(users.get(userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return users.values().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}
