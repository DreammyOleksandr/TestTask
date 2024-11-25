package com.dreammy.server.responses;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricTypeResponse {
    @Id
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    public Long getId() {
        return id;
    }

    private String normalizedName;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }
}
