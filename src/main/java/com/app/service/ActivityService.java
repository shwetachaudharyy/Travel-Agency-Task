package com.app.service;

import java.util.List;

import com.app.dto.ActivityDTO;
import com.app.dto.PassengerDTO;
import com.app.entities.SignUpStatusEnum;

public interface ActivityService {
    List<ActivityDTO> getAllActivities();

    ActivityDTO getActivityById(Long activityId);

    ActivityDTO createActivity(ActivityDTO activityDTO, Long destinationId);

    ActivityDTO updateActivity(Long activityId, ActivityDTO activityDTO);

    void deleteActivity(Long activityId);

    List<PassengerDTO> getSignedUpPassengers(Long activityId);

    List<ActivityDTO> getActivitiesWithAvailableSpaces();

    List<ActivityDTO> getActivitiesByDestinationId(Long destinationId);

    SignUpStatusEnum signUpForActivity(Long activityId, Long passengerId);
}
