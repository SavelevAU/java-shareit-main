package ru.practicum.shareit.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Long id;
    private String description;
    private Long requesterId;
    private LocalDateTime created;
}
