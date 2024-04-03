package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Activity;
import com.app.entities.Destination;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    List<Activity> findByDestinationId(Long destinationId);
}