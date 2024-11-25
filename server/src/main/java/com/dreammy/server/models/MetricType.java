package com.dreammy.server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class MetricType {
    public MetricType() {
    }

    public MetricType(String name) {
        this.name = name;
        this.normalizedName = name.toUpperCase();
        this.creationDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Normalized name cannot be blank.")
    private String normalizedName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizedName() {
        return name.toUpperCase();
    }

    public void setNormalizedName() {
        this.normalizedName = name.toUpperCase();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
