package com.dreammy.server.responses;

import com.dreammy.server.models.MetricType;
import com.dreammy.server.models.MetricValue;

import java.time.LocalDateTime;
import java.util.List;

public class MetricValueResponse {

    private Long id;

    private String value;

    private MetricType metricType;

    private LocalDateTime recordedDate;

    List<MetricValue> relatedMetricValues;

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

    public List<MetricValue> getRelatedMetricValues() {
        return relatedMetricValues;
    }

    public void setRelatedMetricValues(List<MetricValue> relatedMetricValues) {
        this.relatedMetricValues = relatedMetricValues;
    }
}
