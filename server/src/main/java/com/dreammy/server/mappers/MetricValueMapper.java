package com.dreammy.server.mappers;

import com.dreammy.server.models.MetricValue;
import com.dreammy.server.requests.metricValue.MetricValueCreateRequest;
import com.dreammy.server.requests.metricValue.MetricValueUpdateRequest;
import com.dreammy.server.responses.MetricValueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetricValueMapper {
    MetricValueMapper INSTANCE = Mappers.getMapper(MetricValueMapper.class);

    MetricValue toModel(MetricValueUpdateRequest request);
    MetricValue toModel(MetricValueCreateRequest request);
    MetricValueResponse toResponse(MetricValue metricValue);

    List<MetricValueResponse> toResposeList(List<MetricValue> metricValues);
}
