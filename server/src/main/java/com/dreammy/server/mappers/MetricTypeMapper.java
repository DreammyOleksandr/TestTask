package com.dreammy.server.mappers;

import com.dreammy.server.requests.metricType.MetricTypeUpdateRequest;
import com.dreammy.server.responses.MetricTypeResponse;
import com.dreammy.server.models.MetricType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetricTypeMapper {
    MetricTypeMapper INSTANCE = Mappers.getMapper(MetricTypeMapper.class);

    MetricType toModel(MetricTypeUpdateRequest metricTypeUpdateRequest);
    MetricTypeResponse toResponse(MetricType metricType);

    List<MetricTypeResponse> toResposeList(List<MetricType> metricTypes);
}
