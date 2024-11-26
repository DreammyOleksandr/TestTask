package com.dreammy.server.services;

import com.dreammy.server.exceptions.metricType.MetricTypeNotFoundException;
import com.dreammy.server.exceptions.metricType.SeedingDefaultMetricTypesException;
import com.dreammy.server.models.MetricType;
import com.dreammy.server.repositories.MetricTypeRepository;
import com.dreammy.server.services.interfaces.IMetricTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetricTypeService implements IMetricTypeService {

    private final MetricTypeRepository metricTypeRepository;

    public MetricTypeService(MetricTypeRepository metricTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
    }

    public List<MetricType> getAll() {
        List<MetricType> metricTypes = metricTypeRepository.findAll();
        if (metricTypes.isEmpty()) {
            throw new MetricTypeNotFoundException("Metric types not found");
        }
        return metricTypes;
    }

    public MetricType getById(Long id) {
        return metricTypeRepository.findById(id)
                .orElseThrow(() -> new MetricTypeNotFoundException("Metric type not found with id: " + id));
    }

    @Transactional
    public List<MetricType> createDefault() {
        ensureMetricTypesAreEmpty();
        return createAndSaveDefaultMetricTypes();
    }


    public MetricType update(Long id, MetricType updatedMetricType) {
        MetricType existingMetric = getById(id);
        existingMetric.setName(updatedMetricType.getName());
        return metricTypeRepository.save(existingMetric);
    }

    public void deleteAll() {
        if (metricTypeRepository.count() == 0) {
            throw new MetricTypeNotFoundException("Metric types not found");
        }
        metricTypeRepository.deleteAll();
    }

    private void ensureMetricTypesAreEmpty() {
        if (!metricTypeRepository.findAll().isEmpty()) {
            throw new SeedingDefaultMetricTypesException("To seed default metric types the list of metric types must be empty");
        }
    }

    private List<MetricType> createAndSaveDefaultMetricTypes() {
        List<MetricType> defaultMetricTypes = List.of(
                new MetricType("Temperature", "FLOAT"),
                new MetricType("SubstanceMass", "INT"),
                new MetricType("UserSatisfaction", "BOOLEAN")
        );
        return metricTypeRepository.saveAll(defaultMetricTypes);
    }
}
