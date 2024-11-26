package com.dreammy.server.requests.metricValue;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MetricValueUpsertRequest {

    @NotNull
    private Long metricTypeId;

    @NotNull(message = "Value cannot be null.")
    private String value;

    private List<Long> relationIds;

    public String getValue() {
        return value;
    }

    public Long getMetricTypeId() {
        return metricTypeId;
    }

    public List<Long> getRelationIds() {
        return relationIds;
    }

    public void setRelationIds(List<Long> relationIds) {
        this.relationIds = relationIds;
    }

}
