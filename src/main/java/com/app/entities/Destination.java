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
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id")
    private Long destinationId;

    @Column(name = "destination_name", nullable = false)
    @Length(min = 2, max = 30, message = "Invalid length of destination name")
    private String destinationName;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    List<Activity> activityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package packageId;
}
