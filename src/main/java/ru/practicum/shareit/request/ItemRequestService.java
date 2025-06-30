package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto createItemRequest(Long userId, ItemRequestDto dto);

    List<ItemRequestDto> getAllRequestsByUser(Long userId);

    List<ItemRequestDto> getAllRequests();

    ItemRequestDto getRequestById(Long requestId);
}
