package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryBookingService implements BookingService {
    private final Map<Long, Booking> bookings = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public BookingDto createBooking(Long bookerId, BookingDto bookingDto) {
        Booking booking = BookingMapper.toBooking(bookingDto, bookerId, "WAITING");
        booking.setId(idCounter.getAndIncrement());
        bookings.put(booking.getId(), booking);
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return null;
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsByBooker(Long bookerId) {
        return bookings.values().stream()
                .filter(b -> b.getBookerId().equals(bookerId))
                .map(BookingMapper::toBookingDto)
                .toList();
    }

    @Override
    public List<BookingDto> getAllBookingsByOwner(Long ownerId) {
        return bookings.values().stream()
                .filter(b -> b.getItemId().equals(ownerId))
                .map(BookingMapper::toBookingDto)
                .toList();
    }

    @Override
    public BookingDto approveBooking(Long ownerId, Long bookingId, Boolean approved) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return null;
        booking.setStatus(approved ? "APPROVED" : "REJECTED");
        return BookingMapper.toBookingDto(booking);
    }
}
