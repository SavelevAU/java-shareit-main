package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryItemService implements ItemService {
    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto, userId);
        item.setId(idCounter.getAndIncrement());
        items.put(item.getId(), item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = items.get(itemId);
        if (item == null || !item.getOwnerId().equals(userId)) return null;
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
        if (text.isBlank()) return List.of();
        String lowerText = text.toLowerCase();
        return items.values().stream()
                .filter(i -> i.getAvailable()
                        && (i.getName().toLowerCase().contains(lowerText)
                        || i.getDescription().toLowerCase().contains(lowerText)))
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
