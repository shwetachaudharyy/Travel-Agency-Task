package com.app.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.BookingDTO;
import com.app.dto.PackageDTO;
import com.app.entities.Booking;
import com.app.entities.Package;
import com.app.entities.Passenger;
import com.app.entities.User;
import com.app.repository.BookingRepository;
import com.app.repository.PassengerRepository;
import com.app.repository.UserRepository;
import com.app.service.BookingService;
import com.app.service.PackageService;

@SuppressWarnings("null")
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PackageService packageService;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookingDTO bookPackage(Long userId, Long packageId) {
        PackageDTO packageEntity = packageService.getPackageById(packageId);
        Package packageObj = modelMapper.map(packageEntity, Package.class);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
        Passenger passenger = this.passengerRepository.findById(user.getPassenger().getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId",
                        user.getPassenger().getPassengerId()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookedPackage(packageObj);
        booking.setPassenger(passenger);
        booking.setBookingDate(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);

        return convertToDTO(savedBooking);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookingDTO> getUserBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getPackageBookings(Long packageId) {
        List<Booking> bookings = bookingRepository.findByPackageId(packageId);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO convertToDTO(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }
}
