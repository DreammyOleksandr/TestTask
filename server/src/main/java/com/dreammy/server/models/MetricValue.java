package com.dreammy.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MetricValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Value cannot be null.")
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "metric_type_id", nullable = false)
    private MetricType metricType;

    @Column(nullable = false)
    private LocalDateTime recordedDate;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<MetricValueRelation> relations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public LocalDateTime getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(LocalDateTime recordedDate) {
        this.recordedDate = recordedDate;
    }

    public List<MetricValueRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<MetricValueRelation> relations) {
        this.relations = relations;
    }

}

