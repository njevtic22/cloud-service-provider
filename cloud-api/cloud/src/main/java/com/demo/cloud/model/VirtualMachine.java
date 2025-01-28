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

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private Category category;

    public VirtualMachine() { }

    public VirtualMachine(String name, Organization organization, Category category) {
        this(null, name, organization, category);
    }

    public VirtualMachine(Long id, String name, Organization organization, Category category) {
        this.id = id;
        this.name = Strings.requireNonBlank(name, "Name must not be blank.");
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

    public int getCpu() {
        return category.getCpu();
    }

    public int getRam() {
        return category.getRam();
    }

    public int getGpu() {
        return category.getGpu();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Category getCategory() {
        return category;
    }
}
