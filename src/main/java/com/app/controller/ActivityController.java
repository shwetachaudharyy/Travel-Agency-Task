package com.app.controller;

import com.app.dto.ActivityDTO;
import com.app.dto.PassengerDTO;
import com.app.entities.SignUpStatusEnum;
import com.app.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/all")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long activityId) {
        ActivityDTO activityDTO = activityService.getActivityById(activityId);
        return new ResponseEntity<>(activityDTO, HttpStatus.OK);
    }

    @PostMapping("/create/{destinationId}")
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO activityDTO,
            @PathVariable Long destinationId) {
        ActivityDTO createdActivity = activityService.createActivity(activityDTO, destinationId);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @PutMapping("/update/{activityId}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long activityId,
            @RequestBody ActivityDTO activityDTO) {
        ActivityDTO updatedActivity = activityService.updateActivity(activityId, activityDTO);
        return new ResponseEntity<>(updatedActivity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{activityId}/passengers")
    public ResponseEntity<List<PassengerDTO>> getSignedUpPassengers(@PathVariable Long activityId) {
        List<PassengerDTO> passengers = activityService.getSignedUpPassengers(activityId);
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @GetMapping("/availableSpaces")
    public ResponseEntity<List<ActivityDTO>> getActivitiesWithAvailableSpaces() {
        List<ActivityDTO> activities = activityService.getActivitiesWithAvailableSpaces();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @PostMapping("/{activityId}/signUp/{passengerId}")
    public ResponseEntity<SignUpStatusEnum> signUpForActivity(@PathVariable Long activityId,
            @PathVariable Long passengerId) {
        SignUpStatusEnum status = activityService.signUpForActivity(activityId, passengerId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
