package com.dreammy.server.services;

import com.dreammy.server.exceptions.MetricTypeNotFoundException;
import com.dreammy.server.exceptions.SeedingDefaultMetricTypesException;
import com.dreammy.server.models.MetricType;
import com.dreammy.server.repositories.MetricTypeRepository;
import com.dreammy.server.services.interfaces.IMetricTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MetricTypeService implements IMetricTypeService {

    public MetricTypeService(MetricTypeRepository metricTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
    }

    private final MetricTypeRepository metricTypeRepository;

    public List<MetricType> getAllMetricTypes() {
        List<MetricType> metricTypes = metricTypeRepository.findAll();
        if (metricTypes.isEmpty()) {
            throw new MetricTypeNotFoundException("Metric types not found");
        }
        return metricTypes;
    }

    public MetricType getMetricTypeById(Long id) {
        return metricTypeRepository.findById(id)
                .orElseThrow(() -> new MetricTypeNotFoundException("Metric type not found with id: " + id));
    }

    @Transactional
    public List<MetricType> createDefaultMetricTypes() {
        List<MetricType> defaultMetricTypes = metricTypeRepository.findAll();
        if (!defaultMetricTypes.isEmpty()) {
            throw new SeedingDefaultMetricTypesException("To seed default metric types the list of metric types must be empty");
        }

        defaultMetricTypes = Arrays.asList(
                new MetricType("Temperature"),
                new MetricType("SubstanceMass"),
                new MetricType("UserSatisfaction")
        );
        return metricTypeRepository.saveAll(defaultMetricTypes);
    }

    public MetricType updateMetricType(Long id, MetricType updatedMetricType) {
        Optional<MetricType> existingMetricOpt = metricTypeRepository.findById(id);

        if (existingMetricOpt.isEmpty()) {
            throw new MetricTypeNotFoundException("Metric not found with id: " + id);
        }

        MetricType existingMetricType = existingMetricOpt.get();
        existingMetricType.setName(updatedMetricType.getName());
        return metricTypeRepository.save(existingMetricType);
    }

    public void deleteAllMetricTypes() {
        if (metricTypeRepository.findAll().isEmpty()) {
            throw new MetricTypeNotFoundException("Metric types not found");
        }
        metricTypeRepository.deleteAll();
    }
}
