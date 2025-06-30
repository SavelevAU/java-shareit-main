package ru.practicum.shareit.item.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
}
