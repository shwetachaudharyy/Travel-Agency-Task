package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.entities.Activity;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByDestinationId(Long destinationId);

    Activity findByActivityId(Long activityId);

    List<Activity> printActivitiesWithAvailableSpaces(Long destinationId);
}
