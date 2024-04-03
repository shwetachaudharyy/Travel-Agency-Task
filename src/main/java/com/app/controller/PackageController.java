package com.app.controller;

import com.app.dto.PackageDTO;
import com.app.dto.PassengerDTO;
import com.app.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
@CrossOrigin(origins = "http://localhost:3000")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/all")
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        List<PackageDTO> packages = packageService.getAllPackages();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable Long packageId) {
        PackageDTO packageDTO = packageService.getPackageById(packageId);
        return new ResponseEntity<>(packageDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PackageDTO> createPackage(@RequestBody PackageDTO packageDTO) {
        PackageDTO createdPackage = packageService.createPackage(packageDTO);
        return new ResponseEntity<>(createdPackage, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long packageId) {
        packageService.deletePackage(packageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/itinerary/{packageId}")
    public ResponseEntity<String> printItinerary(@PathVariable Long packageId) {
        String itinerary = packageService.printItinerary(packageId);
        return new ResponseEntity<>(itinerary, HttpStatus.OK);
    }

    @GetMapping("/passenger-list/{packageId}")
    public ResponseEntity<List<PassengerDTO>> printPassengerList(@PathVariable Long packageId) {
        List<PassengerDTO> passengerList = packageService.printPassengerList(packageId);
        return new ResponseEntity<>(passengerList, HttpStatus.OK);
    }
}
