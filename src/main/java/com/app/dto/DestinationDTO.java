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
public class DestinationDTO {

    private Long destinationId;
    private String destinationName;
    private List<ActivityDTO> activityList;
    private PackageDTO packageId;
}
