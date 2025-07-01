package ru.practicum.shareit.booking;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private Long id;
    private Long itemId;
    private Long bookerId;
    private String start;
    private String end;
    private String status; // "WAITING", "APPROVED", "REJECTED"
}
