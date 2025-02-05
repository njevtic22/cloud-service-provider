package com.demo.cloud.model;

import com.demo.cloud.util.Strings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_generator")
    @SequenceGenerator(name = "organization_generator", sequenceName = "organization_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column
    private String logo;

    @Column(nullable = false)
    private boolean archived;

    public Organization() { }

    public Organization(String name, String description, String logo, boolean archived) {
        this(null, name, description, logo, archived);
    }

    public Organization(Long id, String name, String description, String logo, boolean archived) {
        this.id = id;
        this.name = Strings.requireNonBlank(name, "Name must not be blank.");
        this.description = Strings.requireNonBlank(description, "Description must not be blank.");
        this.logo = logo;
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization that)) return false;
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

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public boolean isArchived() {
        return archived;
    }
}
