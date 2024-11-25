package com.dreammy.server.mappers;

import com.dreammy.server.DTOs.MetricTypeDto;
import com.dreammy.server.models.MetricType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetricTypeMapper {
    MetricTypeMapper INSTANCE = Mappers.getMapper(MetricTypeMapper.class);

    MetricType toModel(MetricTypeDto metricTypeDto);
    MetricTypeDto toDto(MetricType metricType);

    List<MetricTypeDto> toDtoList(List<MetricType> metricTypes);
}
