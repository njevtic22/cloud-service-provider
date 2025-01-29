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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean archived;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Organization organization;

    public User() { }

    public User(String name, String surname, String email, String username, String password, Role role, boolean archived, Organization organization) {
        this(null, name, surname, email, username, password, archived, role, organization);
    }

    public User(Long id, String name, String surname, String email, String username, String password, boolean archived, Role role, Organization organization) {
        this.id = id;
        this.name = Strings.requireNonBlank(name, "Name must not be blank.");
        this.surname = Strings.requireNonBlank(surname, "Surname must not be blank.");
        this.email = Strings.requireNonBlank(email, "Email must not be blank.");
        this.username = Strings.requireNonBlank(username, "Username must not be blank.");
        this.password = Objects.requireNonNull(password, "Password must not be null.");       // could be blank
        this.archived = archived;
        this.role = role;
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isArchived() {
        return archived;
    }

    public Role getRole() {
        return role;
    }

    public Organization getOrganization() {
        return organization;
    }
}
