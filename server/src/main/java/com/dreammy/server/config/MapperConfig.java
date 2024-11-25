package com.dreammy.server.config;

import com.dreammy.server.mappers.MetricTypeMapper;
import com.dreammy.server.mappers.MetricTypeMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public MetricTypeMapper metricTypeMapper() {
        return new MetricTypeMapperImpl();
    }
}