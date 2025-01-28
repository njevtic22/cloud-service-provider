package com.demo.cloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_generator")
    @SequenceGenerator(name = "activity_generator", sequenceName = "activity_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate turnedOn;

    @Column
    private LocalDate turnedOff;

    @ManyToOne
    private VirtualMachine machines;

    public Activity() { }

    public Activity(LocalDate turnedOn, LocalDate turnedOff, VirtualMachine machines) {
        this(null, turnedOn, turnedOff, machines);
    }

    public Activity(Long id, LocalDate turnedOn, LocalDate turnedOff, VirtualMachine machines) {
        this.id = id;
        this.turnedOn = Objects.requireNonNull(turnedOn, "Turned on date must not be null.");
        this.turnedOff = turnedOff;
        this.machines = Objects.requireNonNull(machines, "Virtual machine must not be null.");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity activity)) return false;
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getTurnedOn() {
        return turnedOn;
    }

    public LocalDate getTurnedOff() {
        return turnedOff;
    }

    public VirtualMachine getMachines() {
        return machines;
    }
}
