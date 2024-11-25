package com.dreammy.server.responses;

import com.dreammy.server.models.MetricType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MetricValueResponse {

    @Id
    private Long id;

    @NotNull(message = "Value cannot be null.")
    private Double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="metric_type_id", nullable = false)
    private MetricType metricType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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
}
