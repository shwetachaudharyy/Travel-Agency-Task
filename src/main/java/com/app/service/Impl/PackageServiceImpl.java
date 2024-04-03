package com.app.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.PackageDTO;
import com.app.dto.PassengerDTO;
import com.app.entities.Activity;
import com.app.entities.Destination;
import com.app.entities.Package;
import com.app.entities.Passenger;
import com.app.repository.PackageRepository;
import com.app.service.PackageService;

@SuppressWarnings("null")
@Service
@Transactional
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PackageDTO> getAllPackages() {
        List<Package> packages = packageRepository.findAll();
        return packages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PackageDTO getPackageById(Long packageId) {
        Package packageEntity = this.packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("package", "packageId", packageId));
        return convertToDto(packageEntity);
    }

    @Override
    public PackageDTO createPackage(PackageDTO packageDTO) {
        Package packageEntity = convertToEntity(packageDTO);
        Package savedPackage = packageRepository.save(packageEntity);
        return convertToDto(savedPackage);
    }

    @Override
    public void deletePackage(Long packageId) {
        Package package1 = this.packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("package", "packageId", packageId));
        this.packageRepository.delete(package1);
    }

    @Override
    public String printItinerary(Long packageId) {
        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("package", "packageId", packageId));

        StringBuilder itinerary = new StringBuilder();
        itinerary.append("Travel Package: ").append(pkg.getPackageName()).append("\n\n");

        for (Destination destination : pkg.getItinerary()) {
            itinerary.append("Destination: ").append(destination.getDestinationName()).append("\n");
            for (Activity activity : destination.getActivityList()) {
                itinerary.append("\tActivity: ").append(activity.getActivityName()).append("\n")
                        .append("\tCost: ").append(activity.getActivityCost()).append("\n")
                        .append("\tCapacity: ").append(activity.getActivityCapacity()).append("\n\n");
            }
        }

        return itinerary.toString();
    }

    @Override
    public List<PassengerDTO> printPassengerList(Long packageId) {
        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("package", "packageId", packageId));
        List<Passenger> passengers = pkg.getPassengerList();
        return passengers.stream()
                .map(passenger -> modelMapper.map(passenger, PassengerDTO.class))
                .collect(Collectors.toList());
    }

    private PackageDTO convertToDto(Package packageEntity) {
        return modelMapper.map(packageEntity, PackageDTO.class);
    }

    private Package convertToEntity(PackageDTO packageDTO) {
        return modelMapper.map(packageDTO, Package.class);
    }
}
