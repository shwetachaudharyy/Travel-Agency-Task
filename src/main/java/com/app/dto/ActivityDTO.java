package com.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivityDTO {

    private Long activityId;
    private String activityName;
    private Integer activityCost;
    private Integer activityCapacity;
    private Integer availableSlots;
    private DestinationDTO destinationId;
    private List<PassengerDTO> passengerList;
}
