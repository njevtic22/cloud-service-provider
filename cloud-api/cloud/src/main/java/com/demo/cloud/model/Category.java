package com.demo.cloud.model;

import com.demo.cloud.core.error.exceptions.ModelConstraintException;
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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int cpu;

    @Column(nullable = false)
    private int ram;

    @Column(nullable = false)
    private int gpu;

    @Column(nullable = false)
    private boolean archived;

    public Category() { }

    public Category(String name, int cpu, int ram, int gpu, boolean archived) {
        this(null, name, cpu, ram, gpu, archived);
    }

    public Category(Long id, String name, int cpu, int ram, int gpu, boolean archived) {
        if (cpu <= 0) {
            throw new ModelConstraintException("Number of cpu cores must be positive integer");
        }
        if (ram <= 0) {
            throw new ModelConstraintException("Capacity of ram must be positive integer");
        }
        if (gpu < 0) {
            throw new ModelConstraintException("Number of gpu cores must be positive integer or zero");
        }

        this.id = id;
        this.name = Strings.requireNonBlank(name, "Name must not be blank.");
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
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

    public int getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getGpu() {
        return gpu;
    }

    public boolean isArchived() {
        return archived;
    }
}
