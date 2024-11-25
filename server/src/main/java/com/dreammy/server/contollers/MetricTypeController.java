package com.dreammy.server.contollers;

import com.dreammy.server.DTOs.MetricTypeDto;
import com.dreammy.server.mappers.MetricTypeMapper;
import com.dreammy.server.models.MetricType;
import com.dreammy.server.service.MetricTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metricTypes")
public class MetricTypeController {

    private final MetricTypeService metricTypeService;
    private final MetricTypeMapper metricTypeMapper;

    public MetricTypeController(MetricTypeService metricTypeService, MetricTypeMapper metricTypeMapper) {
        this.metricTypeService = metricTypeService;
        this.metricTypeMapper = metricTypeMapper;
    }

    @GetMapping()
    public ResponseEntity<List<MetricType>> getAllMetricTypes() {
        List<MetricType> metricTypes = metricTypeService.getAllMetricTypes();
        return ResponseEntity.ok(metricTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricType> getMetricTypeById(@PathVariable Long id) {
        MetricType metricType = metricTypeService.getMetricTypeById(id);
        return ResponseEntity.ok(metricType);
    }

    @PostMapping("/default")
    public ResponseEntity<List<MetricType>> createDefaultMetricTypes() {
        List<MetricType> metricTypes = metricTypeService.createDefaultMetricTypes();
        return new ResponseEntity<>(metricTypes, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricType> updateMetricType(@PathVariable Long id, @RequestBody MetricType updatedMetricType) {
        MetricType updatedResult = metricTypeService.updateMetricType(id, updatedMetricType);
        return ResponseEntity.ok(updatedResult);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllMetricTypes() {
        metricTypeService.deleteAllMetricTypes();
    }
}
