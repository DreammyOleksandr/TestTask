package com.dreammy.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MetricType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Normalized name cannot be blank.")
    private String normalizedName;

    @NotBlank(message = "type cannot be blank.")
    private String type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "metricType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MetricValue> metricValues;

    public MetricType() {
    }

    public MetricType(String name, String type) {
        this.name = name;
        this.type = type;
        this.creationDate = LocalDateTime.now();
        this.normalizedName = name.toUpperCase();
    }

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

    public List<MetricValue> getMetricValues() {
        return metricValues;
    }

    public void setMetricValues(List<MetricValue> metricValues) {
        this.metricValues = metricValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
