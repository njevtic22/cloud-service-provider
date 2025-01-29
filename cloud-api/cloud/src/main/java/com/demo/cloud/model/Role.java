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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Role() { }

    public Role(String name) {
        this.name = Strings.requireNonBlank(name, "Role name must not be blank.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public boolean isSuperAdmin() {
        return name.equals("ROLE_SUPER_ADMIN");
    }

    public boolean isAdmin() {
        return name.equals("ROLE_ADMIN");
    }

    public boolean isUser() {
        return name.equals("ROLE_USER");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
