package com.app.controller;

import com.app.dto.ActivityDTO;
import com.app.dto.DestinationDTO;
import com.app.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destination")
@CrossOrigin(origins = "http://localhost:3000")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping("/all")
    public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
        List<DestinationDTO> destinations = destinationService.getAllDestinations();
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @GetMapping("/{destinationId}")
    public ResponseEntity<DestinationDTO> getDestinationById(@PathVariable Long destinationId) {
        DestinationDTO destinationDTO = destinationService.getDestinationById(destinationId);
        return new ResponseEntity<>(destinationDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DestinationDTO> createDestination(@RequestBody DestinationDTO destinationDTO) {
        DestinationDTO createdDestination = destinationService.createDestination(destinationDTO);
        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }

    @PutMapping("/update/{destinationId}")
    public ResponseEntity<DestinationDTO> updateDestination(@PathVariable Long destinationId,
            @RequestBody DestinationDTO destinationDTO) {
        DestinationDTO updatedDestination = destinationService.updateDestination(destinationId, destinationDTO);
        return new ResponseEntity<>(updatedDestination, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{destinationId}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long destinationId) {
        destinationService.deleteDestination(destinationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{destinationId}/activities")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByDestinationId(@PathVariable Long destinationId) {
        List<ActivityDTO> activities = destinationService.getActivities(destinationId);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
}
