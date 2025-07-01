package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

public class BookingMapper {
    public static Booking toBooking(BookingDto dto, Long bookerId, String status) {
        return new Booking(dto.getId(), dto.getItemId(), bookerId, dto.getStart(), dto.getEnd(), status);
    }

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getItemId(), booking.getStart(), booking.getEnd());
    }
}
