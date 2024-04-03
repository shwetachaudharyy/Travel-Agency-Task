package com.app.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "package_name", nullable = false)
    @Length(min = 2, max = 30, message = "Invalid length of package name")
    private String packageName;

    @Column(name = "passenger_capacity")
    @Min(value = 0)
    private Integer passengerCapacity;

    @OneToMany(mappedBy = "package", cascade = CascadeType.ALL)
    List<Destination> itinerary = new ArrayList<>();

    @OneToMany(mappedBy = "package", cascade = CascadeType.ALL)
    List<Passenger> passengerList = new ArrayList<>();
}
