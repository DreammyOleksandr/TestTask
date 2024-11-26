package com.dreammy.server.services.interfaces;

import com.dreammy.server.models.MetricValue;
import com.dreammy.server.requests.metricValue.MetricValueCreateRequest;
import com.dreammy.server.requests.metricValue.MetricValueUpdateRequest;
import com.dreammy.server.responses.MetricValueResponse;

import java.util.List;

public interface IMetricValueService {
    MetricValueResponse create(MetricValueCreateRequest request);
    List<MetricValue> getAll();
    List<MetricValue> getPrevious(Long id);
    MetricValue update(Long id, MetricValueUpdateRequest request);
    void delete(Long id);
}
