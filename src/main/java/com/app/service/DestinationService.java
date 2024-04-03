package com.app.service;

import java.util.List;

import com.app.dto.ActivityDTO;
import com.app.dto.DestinationDTO;

public interface DestinationService {
    List<DestinationDTO> getAllDestinations();

    DestinationDTO getDestinationById(Long destinationId);

    DestinationDTO createDestination(DestinationDTO destinationDTO);

    DestinationDTO updateDestination(Long destinationId, DestinationDTO destinationDTO);

    void deleteDestination(Long destinationId);

    List<ActivityDTO> getActivities(Long destinationId);
}
