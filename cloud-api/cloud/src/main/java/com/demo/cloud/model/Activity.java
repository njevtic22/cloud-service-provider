package com.demo.cloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_generator")
    @SequenceGenerator(name = "activity_generator", sequenceName = "activity_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime turnedOn;

    @Column
    private LocalDateTime turnedOff;

    @Column(nullable = false)
    private float profit;

    @ManyToOne
    private VirtualMachine machine;

    public Activity() { }

    public Activity(LocalDateTime turnedOn, LocalDateTime turnedOff, VirtualMachine machine, float profit) {
        this(null, turnedOn, turnedOff, profit, machine);
    }

    public Activity(Long id, LocalDateTime turnedOn, LocalDateTime turnedOff, float profit, VirtualMachine machine) {
        if (profit < 0) {
            throw new IllegalArgumentException("Profit must be positive number or zero.");
        }

        this.id = id;
        this.turnedOn = Objects.requireNonNull(turnedOn, "Turned on date must not be null.");
        this.turnedOff = turnedOff;
        this.profit = profit;
        this.machine = Objects.requireNonNull(machine, "Virtual machine must not be null.");

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

    public LocalDateTime getTurnedOn() {
        return turnedOn;
    }

    public LocalDateTime getTurnedOff() {
        return turnedOff;
    }

    public float getProfit() {
        return profit;
    }

    public VirtualMachine getMachine() {
        return machine;
    }
}
