package com.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.dto.BookingDTO;
import com.app.entities.Booking;
import com.app.repository.BookingRepository;
import com.app.service.Impl.BookingServiceImpl;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class TestBookingService {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void bookPackage_ValidUserIdAndPackageId_ReturnsBookingDTO() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(createDummyBooking());
        BookingDTO result = bookingService.bookPackage(1L, 1L);
        assertEquals(createDummyBookingDTO(), result);
    }

    @Test
    void cancelBooking_ValidBookingId_ReturnsVoid() {
        bookingService.cancelBooking(1L);
        verify(bookingRepository).deleteById(1L);
    }

    @Test
    void getUserBookings_ValidUserId_ReturnsListOfBookingDTO() {
        when(bookingRepository.findByUserId(1L)).thenReturn(createDummyBookings());
        List<BookingDTO> result = bookingService.getUserBookings(1L);
        assertEquals(createDummyBookingDTOList(), result);
    }

    @Test
    void getPackageBookings_ValidPackageId_ReturnsListOfBookingDTO() {
        when(bookingRepository.findByPackageId(1L)).thenReturn(createDummyBookings());
        List<BookingDTO> result = bookingService.getPackageBookings(1L);
        assertEquals(createDummyBookingDTOList(), result);
    }

    private Booking createDummyBooking() {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        return booking;
    }

    private List<Booking> createDummyBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(createDummyBooking());
        return bookings;
    }

    private BookingDTO createDummyBookingDTO() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(1L);
        return bookingDTO;
    }

    private List<BookingDTO> createDummyBookingDTOList() {
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        bookingDTOs.add(createDummyBookingDTO());
        return bookingDTOs;
    }
}
