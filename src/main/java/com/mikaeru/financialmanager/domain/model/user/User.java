package com.mikaeru.financialmanager.domain.model.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private UUID externalId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private final OffsetDateTime createdAt = OffsetDateTime.now();

    /***
     * Hibernate only
     */
    public User() { }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.externalId = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(externalId, user.externalId) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId, email);
    }
}
