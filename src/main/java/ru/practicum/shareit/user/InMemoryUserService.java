package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryUserService implements UserService {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public UserDto createUser(UserDto userDto) {
        if (users.values().stream()
                .anyMatch(u -> u.getEmail().equals(userDto.getEmail()))) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = UserMapper.toUser(userDto);
        user.setId(idCounter.getAndIncrement());
        users.put(user.getId(), user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existing = users.get(userId);
        if (existing == null) return null;

        // Проверка, что email не занят другим пользователем
        Optional<User> duplicate = users.values().stream()
                .filter(u -> u.getEmail().equals(userDto.getEmail()))
                .findFirst();

        if (duplicate.isPresent() && !duplicate.get().getId().equals(userId)) {
            throw new IllegalArgumentException("Email is already in use");
        }

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
        return Optional.ofNullable(users.get(userId))
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return users.values().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}
