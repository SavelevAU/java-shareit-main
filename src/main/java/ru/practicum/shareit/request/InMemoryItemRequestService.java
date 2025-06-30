package ru.practicum.shareit.request;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryItemRequestService implements ItemRequestService {
    private final Map<Long, ItemRequest> requests = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public ItemRequestDto createItemRequest(Long userId, ItemRequestDto dto) {
        ItemRequest request = ItemRequestMapper.toItemRequest(dto, userId);
        request.setId(idCounter.getAndIncrement());
        request.setCreated(LocalDateTime.now());
        requests.put(request.getId(), request);
        return ItemRequestMapper.toItemRequestDto(request);
    }

    @Override
    public List<ItemRequestDto> getAllRequestsByUser(Long userId) {
        return requests.values().stream()
                .filter(r -> r.getRequesterId().equals(userId))
                .map(ItemRequestMapper::toItemRequestDto)
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllRequests() {
        return requests.values().stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .toList();
    }

    @Override
    public ItemRequestDto getRequestById(Long requestId) {
        return ItemRequestMapper.toItemRequestDto(requests.get(requestId));
    }
}
