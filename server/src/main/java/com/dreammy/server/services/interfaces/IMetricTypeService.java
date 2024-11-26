package com.dreammy.server.services.interfaces;

import com.dreammy.server.models.MetricType;

import java.util.List;

public interface IMetricTypeService {
    MetricType getById(Long id);
    List<MetricType> getAll();
    List<MetricType> createDefault();
    MetricType update(Long id, MetricType updatedMetricType);
}
