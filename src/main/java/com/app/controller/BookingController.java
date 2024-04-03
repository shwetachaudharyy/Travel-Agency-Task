package com.app.controller;

import com.app.dto.BookingDTO;
import com.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookPackage")
    public ResponseEntity<BookingDTO> bookPackage(@RequestParam Long userId, @RequestParam Long packageId) {
        BookingDTO bookingDTO = bookingService.bookPackage(userId, packageId);
        return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getUserBookings(@PathVariable Long userId) {
        List<BookingDTO> bookings = bookingService.getUserBookings(userId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<List<BookingDTO>> getPackageBookings(@PathVariable Long packageId) {
        List<BookingDTO> bookings = bookingService.getPackageBookings(packageId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
