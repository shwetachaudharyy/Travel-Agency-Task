package com.app.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ActivityDTO;
import com.app.dto.DestinationDTO;
import com.app.entities.Destination;
import com.app.repository.DestinationRepository;
import com.app.service.ActivityService;
import com.app.service.DestinationService;

@SuppressWarnings("null")
@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ActivityService activityService;

    @Override
    public List<DestinationDTO> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DestinationDTO getDestinationById(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "destinationId", destinationId));
        return convertToDto(destination);
    }

    @Override
    public DestinationDTO createDestination(DestinationDTO destinationDTO) {
        Destination destination = convertToEntity(destinationDTO);
        Destination savedDestination = destinationRepository.save(destination);
        return convertToDto(savedDestination);
    }

    @Override
    public DestinationDTO updateDestination(Long destinationId, DestinationDTO destinationDTO) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("destination", "destinationId", destinationId));
        destination.setDestinationName(destinationDTO.getDestinationName());
        Destination updateddestination = this.destinationRepository.save(destination);
        return convertToDto(updateddestination);
    }

    @Override
    public void deleteDestination(Long destinationId) {
        Destination destination = this.destinationRepository.findById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("destination", "destinationId", destinationId));
        this.destinationRepository.delete(destination);
    }

    @Override
    public List<ActivityDTO> getActivities(Long destinationId) {
        return activityService.getActivitiesByDestinationId(destinationId);
    }

    private DestinationDTO convertToDto(Destination destination) {
        return modelMapper.map(destination, DestinationDTO.class);
    }

    private Destination convertToEntity(DestinationDTO destinationDTO) {
        return modelMapper.map(destinationDTO, Destination.class);
    }
}
