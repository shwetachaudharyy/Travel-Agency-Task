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
public class PackageDTO {

    private Long packageId;
    private String packageName;
    private Integer passengerCapacity;
    private List<DestinationDTO> itinerary;
    private List<PassengerDTO> passengerList;
}
