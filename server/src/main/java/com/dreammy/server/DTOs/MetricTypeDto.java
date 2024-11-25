package com.dreammy.server.DTOs;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricTypeDto {
    public MetricTypeDto(String name, String normalizedName) {
        this.name = name;
        this.normalizedName = normalizedName;
    }

    @Id
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;


    @NotBlank(message = "Normalized name cannot be blank.")
    private String normalizedName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
