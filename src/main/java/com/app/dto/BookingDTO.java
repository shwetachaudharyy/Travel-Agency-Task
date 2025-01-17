package com.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private Long bookingId;
    private UserDTO user;
    private PassengerDTO passenger;
    private PackageDTO bookedPackage;
    private LocalDateTime bookingDate;
}
