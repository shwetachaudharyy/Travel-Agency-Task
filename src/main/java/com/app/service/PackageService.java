package com.app.service;

import java.util.List;

import com.app.dto.PackageDTO;
import com.app.dto.PassengerDTO;

public interface PackageService {
    List<PackageDTO> getAllPackages();

    PackageDTO getPackageById(Long packageId);

    PackageDTO createPackage(PackageDTO packageDTO);

    void deletePackage(Long packageId);

    String printItinerary(Long packageId);

    List<PassengerDTO> printPassengerList(Long packageId);
}
