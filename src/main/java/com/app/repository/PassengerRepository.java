package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findByPackageId(Long packageId);

    Passenger findByPassengerId(Long passengerId);
}