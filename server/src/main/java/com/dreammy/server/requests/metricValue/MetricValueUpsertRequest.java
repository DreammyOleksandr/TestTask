package com.dreammy.server.requests.metricValue;

import jakarta.validation.constraints.NotNull;

public class MetricValueUpsertRequest {

    @NotNull
    private Long metricTypeId;

    @NotNull(message = "Value cannot be null.")
    private Double value;

    public Double getValue() {
        return value;
    }

    public Long getMetricTypeId() {
        return metricTypeId;
    }
}
