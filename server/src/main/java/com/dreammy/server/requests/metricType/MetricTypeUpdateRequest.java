package com.dreammy.server.requests.metricType;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricTypeUpdateRequest {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
