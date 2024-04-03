package com.app.service;

import java.util.List;
import com.app.dto.BookingDTO;

public interface BookingService {
    BookingDTO bookPackage(Long userId, Long packageId);

    void cancelBooking(Long bookingId);

    List<BookingDTO> getUserBookings(Long userId);

    List<BookingDTO> getPackageBookings(Long packageId);
}
