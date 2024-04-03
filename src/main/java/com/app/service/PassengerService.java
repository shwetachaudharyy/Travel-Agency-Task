package com.app.service;

import java.util.List;

import com.app.dto.ActivityDTO;
import com.app.dto.PassengerDTO;

public interface PassengerService {
    List<PassengerDTO> getAllPassengers();

    PassengerDTO getPassengerBypassengerId(Long passengerId);

    PassengerDTO createPassenger(PassengerDTO passengerDTO);

    PassengerDTO updatePassenger(Long passengerId, PassengerDTO passengerDTO);

    void deletePassenger(Long passengerId);

    String printPassengerDetails(Long passengerId);

    List<ActivityDTO> getActivities(Long passengerId);

    PassengerDTO setPassengerTypeAndBalance(PassengerDTO passengerDTO, double amountPaid);
}
