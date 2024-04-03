package com.app.controller;

import com.app.dto.ActivityDTO;
import com.app.dto.PassengerDTO;
import com.app.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:3000")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/all")
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long passengerId) {
        PassengerDTO passengerDTO = passengerService.getPassengerBypassengerId(passengerId);
        return new ResponseEntity<>(passengerDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO createdPassenger = passengerService.createPassenger(passengerDTO);
        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }

    @PutMapping("/update/{passengerId}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable Long passengerId,
            @RequestBody PassengerDTO passengerDTO) {
        PassengerDTO updatedPassenger = passengerService.updatePassenger(passengerId, passengerDTO);
        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/details/{passengerId}")
    public ResponseEntity<String> printPassengerDetails(@PathVariable Long passengerId) {
        String details = passengerService.printPassengerDetails(passengerId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/activities/{passengerId}")
    public ResponseEntity<List<ActivityDTO>> getActivities(@PathVariable Long passengerId) {
        List<ActivityDTO> activities = passengerService.getActivities(passengerId);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @PutMapping("/set-type-balance/{passengerId}")
    public ResponseEntity<PassengerDTO> setPassengerTypeAndBalance(@RequestBody PassengerDTO passengerDTO,
            @RequestParam double amountPaid) {
        PassengerDTO updatedPassenger = passengerService.setPassengerTypeAndBalance(passengerDTO, amountPaid);
        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }
}
