package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();
}
