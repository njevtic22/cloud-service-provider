package com.demo.cloud.model;

import com.demo.cloud.util.Strings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "drives")
public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drive_generator")
    @SequenceGenerator(name = "drive_generator", sequenceName = "drive_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriveType type;

    @Column(nullable = false)
    private boolean archived;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private VirtualMachine machine;

    public Drive() { }

    public Drive(String name, int capacity, DriveType type, Organization organization, VirtualMachine machine, boolean archived) {
        this(null, name, capacity, type, archived, organization, machine);
    }

    public Drive(Long id, String name, int capacity, DriveType type, boolean archived, Organization organization, VirtualMachine machine) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity of drive must be positive integer");
        }

        this.id = id;
        this.name = Strings.requireNonBlank(name, "Name must not be blank.");
        this.capacity = capacity;
        this.type = Objects.requireNonNull(type, "Type must not be null.");
        this.archived = archived;
        this.organization = Objects.requireNonNull(organization, "Organization must not be null.");
        this.machine = Objects.requireNonNull(machine, "Machine must not be null.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drive drive)) return false;
        return Objects.equals(id, drive.id) && Objects.equals(name, drive.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public DriveType getType() {
        return type;
    }

    public boolean isArchived() {
        return archived;
    }

    public Organization getOrganization() {
        return organization;
    }

    public VirtualMachine getMachine() {
        return machine;
    }
}
