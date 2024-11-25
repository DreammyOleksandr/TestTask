package com.dreammy.server.service.interfaces;

import com.dreammy.server.models.MetricType;

import java.util.List;

public interface IMetricTypeService {
    MetricType getMetricTypeById(Long id);
    List<MetricType> getAllMetricTypes();
    List<MetricType> createDefaultMetricTypes();
    MetricType updateMetricType(Long id, MetricType updatedMetricType);
}
