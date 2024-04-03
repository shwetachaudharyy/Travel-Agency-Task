package com.app.dto;

import java.util.List;

import com.app.entities.PassengerTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PassengerDTO {

    private Long passengerId;
    private String passengerName;
    private PassengerTypeEnum passengerType;
    private double balance;
    private PackageDTO packageId;
    private UserDTO userId;
    private List<ActivityDTO> activityList;
}