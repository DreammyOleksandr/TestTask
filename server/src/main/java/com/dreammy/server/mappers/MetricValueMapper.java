package com.dreammy.server.mappers;

import com.dreammy.server.models.MetricValue;
import com.dreammy.server.requests.metricValue.MetricValueUpsertRequest;
import com.dreammy.server.responses.MetricValueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetricValueMapper {
    MetricValueMapper INSTANCE = Mappers.getMapper(MetricValueMapper.class);

    MetricValue toModel(MetricValueUpsertRequest request);
    MetricValueResponse toResponse(MetricValue metricValue);

    List<MetricValueResponse> toResposeList(List<MetricValue> metricValues);
}
