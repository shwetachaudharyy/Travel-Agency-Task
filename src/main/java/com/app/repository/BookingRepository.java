package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findByBookingId(Long bookingId);

    List<Booking> findByPackageId(Long packageId);

    List<Booking> findByUserId(Long userId);
}
