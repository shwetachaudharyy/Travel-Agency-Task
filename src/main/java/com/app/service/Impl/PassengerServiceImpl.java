package com.app.service.Impl;

import com.app.custom_exceptions.PaymentFailureException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ActivityDTO;
import com.app.dto.PassengerDTO;
import com.app.entities.Passenger;
import com.app.entities.PassengerTypeEnum;
import com.app.entities.Activity;
import com.app.repository.PassengerRepository;
import com.app.service.PassengerService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PassengerDTO> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerDTO getPassengerBypassengerId(Long passengerId) {
        Passenger passenger = this.passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId", passengerId));
        return convertToDto(passenger);
    }

    @Override
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = convertToEntity(passengerDTO);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return convertToDto(savedPassenger);
    }

    @Override
    public PassengerDTO updatePassenger(Long passengerId, PassengerDTO passengerDTO) {
        Passenger passenger = this.passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId", passengerId));
        passenger.setPassengerName(passengerDTO.getPassengerName());
        passenger.setPassengerType(passengerDTO.getPassengerType());
        passenger.setBalance(passengerDTO.getBalance());
        Passenger updatedPassenger = this.passengerRepository.save(passenger);
        return convertToDto(updatedPassenger);
    }

    @Override
    public void deletePassenger(Long passengerId) {
        Passenger passenger = this.passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId", passengerId));
        this.passengerRepository.delete(passenger);
    }

    @Override
    public String printPassengerDetails(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId", passengerId));
        StringBuilder sb = new StringBuilder();
        sb.append("Passenger Name: ").append(passenger.getPassengerName()).append("\n");
        sb.append("Passenger Number: ").append(passenger.getPassengerId()).append("\n");
        sb.append("Passenger Balance: ").append(passenger.getBalance()).append("\n");
        sb.append("Passenger Activities:\n");
        for (Activity activity : passenger.getActivityList()) {
            sb.append("Activity Name: ").append(activity.getActivityName()).append("\n");
            sb.append("Destination: ").append(activity.getDestination().getDestinationName()).append("\n");
            sb.append("Price: ").append(activity.getActivityCost()).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<ActivityDTO> getActivities(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId", passengerId));
        List<Activity> activities = passenger.getActivityList();
        return activities.stream()
                .map((getactivity) -> this.modelMapper.map(getactivity, ActivityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PassengerDTO setPassengerTypeAndBalance(PassengerDTO passengerDTO, double amountPaid) {
        boolean paymentReceived = amountPaid > 0;
        if (paymentReceived) {
            if (amountPaid == 5000) {
                passengerDTO.setPassengerType(PassengerTypeEnum.STANDARD);
                passengerDTO.setBalance(5000);
            } else if (amountPaid == 10000) {
                passengerDTO.setPassengerType(PassengerTypeEnum.GOLD);
                passengerDTO.setBalance(10000);
            } else if (amountPaid == 25000) {
                passengerDTO.setPassengerType(PassengerTypeEnum.PREMIUM);
                passengerDTO.setBalance(Double.POSITIVE_INFINITY);
            }
            this.updatePassenger(passengerDTO.getPassengerId(), passengerDTO);
        } else {
            throw new PaymentFailureException("Payment failed! Please try again or contact support.");
        }

        return passengerDTO;
    }

    private PassengerDTO convertToDto(Passenger passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    private Passenger convertToEntity(PassengerDTO passengerDTO) {
        return modelMapper.map(passengerDTO, Passenger.class);
    }
}
