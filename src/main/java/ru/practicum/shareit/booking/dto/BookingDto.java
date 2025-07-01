package ru.practicum.shareit.booking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Long itemId;
    private String start;
    private String end;
}
