package com.app.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.InsufficientBalanceException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ActivityDTO;
import com.app.dto.PassengerDTO;
import com.app.entities.Activity;
import com.app.entities.Passenger;
import com.app.entities.PassengerTypeEnum;
import com.app.entities.SignUpStatusEnum;
import com.app.repository.ActivityRepository;
import com.app.repository.PassengerRepository;
import com.app.service.ActivityService;

@SuppressWarnings("null")
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public List<ActivityDTO> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityDTO getActivityById(Long activityId) {
        Activity activity = this.activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("activity", "activityId", activityId));
        return convertToDto(activity);
    }

    @Override
    public ActivityDTO createActivity(ActivityDTO activityDTO, Long destinationId) {
        Activity activity = convertToEntity(activityDTO);
        Activity savedActivity = activityRepository.save(activity);
        return convertToDto(savedActivity);
    }

    @Override
    public ActivityDTO updateActivity(Long activityId, ActivityDTO activityDTO) {
        Activity activity = this.activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("activity", "activityid", activityId));
        activity.setActivityName(activityDTO.getActivityName());
        activity.setActivityCost(activityDTO.getActivityCost());
        activity.setActivityCapacity(activityDTO.getActivityCapacity());
        Activity updatedActivity = this.activityRepository.save(activity);

        return convertToDto(updatedActivity);
    }

    @Override
    public void deleteActivity(Long activityId) {
        Activity activity = this.activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("activity", "activityId", activityId));
        this.activityRepository.delete(activity);
    }

    @Override
    public List<PassengerDTO> getSignedUpPassengers(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("activity", "activityId", activityId));
        List<Passenger> passengers = activity.getPassengerList();
        return passengers.stream()
                .map((getpassenger) -> this.modelMapper.map(getpassenger, PassengerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTO> getActivitiesWithAvailableSpaces() {
        List<Activity> activities = activityRepository.findAll();
        List<ActivityDTO> availableActivities = new ArrayList<>();
        for (Activity activity : activities) {
            int availableSlots = activity.getActivityCapacity() - activity.getPassengerList().size();
            if (availableSlots > 0) {
                ActivityDTO activityDTO = this.modelMapper.map(activity, ActivityDTO.class);
                activityDTO.setAvailableSlots(availableSlots);
                availableActivities.add(activityDTO);
            }
        }
        return availableActivities;
    }

    @Override
    public List<ActivityDTO> getActivitiesByDestinationId(Long destinationId) {
        List<Activity> activities = activityRepository.findByDestinationId(destinationId);
        return activities.stream()
                .map((getactivity) -> this.modelMapper.map(getactivity, ActivityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SignUpStatusEnum signUpForActivity(Long activityId, Long passengerId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("activity", "activityId", activityId));

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("passenger", "passengerId", passengerId));

        int signedUpPassengers = activity.getPassengerList().size();
        int availableSlots = activity.getActivityCapacity() - signedUpPassengers;

        if (availableSlots > 0) {
            if (passenger.getPassengerType() != PassengerTypeEnum.PREMIUM
                    && passenger.getBalance() < activity.getActivityCost()) {
                throw new InsufficientBalanceException("Insufficient balance to sign up for the activity.");
            }
            double costToDeduct = activity.getActivityCost();
            if (passenger.getPassengerType() == PassengerTypeEnum.GOLD) {
                costToDeduct *= 0.9;
                passenger.setBalance(passenger.getBalance() - costToDeduct);
            }
            if (passenger.getPassengerType() != PassengerTypeEnum.PREMIUM) {
                passenger.setBalance(passenger.getBalance() - costToDeduct);
            }
            passenger.getActivityList().add(activity);
            activity.setActivityCapacity(activity.getActivityCapacity() - 1);
            activity.getPassengerList().add(passenger);
            activityRepository.save(activity);
            passengerRepository.save(passenger);

            return SignUpStatusEnum.SUCCESS;
        } else {

            return SignUpStatusEnum.ACTIVITY_FULL;
        }
    }

    private ActivityDTO convertToDto(Activity activity) {
        return modelMapper.map(activity, ActivityDTO.class);
    }

    private Activity convertToEntity(ActivityDTO activityDTO) {
        return modelMapper.map(activityDTO, Activity.class);
    }
}
