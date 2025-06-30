package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;

public class ItemRequestMapper {
    public static ItemRequest toItemRequest(ItemRequestDto dto, Long requesterId) {
        return new ItemRequest(dto.getId(), dto.getDescription(), requesterId, LocalDateTime.now());
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest request) {
        return new ItemRequestDto(request.getId(), request.getDescription(), request.getCreated());
    }
}
