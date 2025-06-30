package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryItemService implements ItemService {
    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final UserService userService;

    public InMemoryItemService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        // Проверяем, существует ли пользователь
        userService.getUserById(userId);

        Item item = ItemMapper.toItem(itemDto, userId);
        item.setId(idCounter.getAndIncrement());
        items.put(item.getId(), item);

        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = items.get(itemId);
        if (item == null) return null;

        // Проверяем, является ли текущий пользователь владельцем
        if (!item.getOwnerId().equals(userId)) {
            throw new NotFoundException("Item with id=" + itemId + " not found for user=" + userId);
        }

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        return ItemMapper.toItemDto(items.get(itemId));
    }

    @Override
    public List<ItemDto> getAllItemsByOwner(Long ownerId) {
        return items.values().stream()
                .filter(i -> i.getOwnerId().equals(ownerId))
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        String lowerText = text.trim().toLowerCase();

        return items.values().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable() &&
                        (item.getName().toLowerCase().contains(lowerText) ||
                                item.getDescription().toLowerCase().contains(lowerText)))
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
