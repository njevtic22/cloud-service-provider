package com.demo.cloud.model;

import com.demo.cloud.util.Strings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "virtual_machines")
public class VirtualMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_generator")
    @SequenceGenerator(name = "machine_generator", sequenceName = "machine_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean archived;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private Category category;

    public VirtualMachine() { }

    public VirtualMachine(String name, boolean active, boolean archived, Organization organization, Category category) {
        this(null, name, active, archived, organization, category);
    }

    public VirtualMachine(Long id, String name, boolean active, boolean archived, Organization organization, Category category) {
        this.id = id;
        this.name = Strings.requireNonBlank(name, "Name must not be blank.");
        this.active = active;
        this.archived = archived;
        this.organization = Objects.requireNonNull(organization, "Organization must not be null.");
        this.category = Objects.requireNonNull(category, "Category must not be null.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualMachine that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
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

    public boolean isActive() {
        return active;
    }

    public boolean isArchived() {
        return archived;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Category getCategory() {
        return category;
    }

    public int getCpu() {
        return category.getCpu();
    }

    public int getRam() {
        return category.getRam();
    }

    public int getGpu() {
        return category.getGpu();
    }
}
