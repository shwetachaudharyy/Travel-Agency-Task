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
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "activity_name", nullable = false)
    @Length(min = 2, max = 30, message = "Invalid length of activity name")
    private String activityName;

    @Column(name = "activity_cost")
    @Min(value = 0)
    private Integer activityCost;

    @Column(name = "activity_capacity")
    @Min(value = 0)
    private Integer activityCapacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<Passenger> passengerList = new ArrayList<>();
}
