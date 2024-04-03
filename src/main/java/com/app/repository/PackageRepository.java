package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.entities.Package;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {

    List<Package> printPackageItinerary(Long packageId);
}
