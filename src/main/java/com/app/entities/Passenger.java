package com.app.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
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
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Long passengerId;

    @Column(name = "passenger_name", nullable = false)
    @Length(min = 2, max = 30, message = "Invalid length of passenger name")
    private String passengerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "passenger_type")
    private PassengerTypeEnum passengerType;

    private double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package packageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "passenger_activity", joinColumns = @JoinColumn(name = "passenger_id"), inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private List<Activity> activityList = new ArrayList<>();
}
