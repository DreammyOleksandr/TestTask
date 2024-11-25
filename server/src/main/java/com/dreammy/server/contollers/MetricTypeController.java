package com.dreammy.server.contollers;

import com.dreammy.server.DTOs.MetricTypeDto;
import com.dreammy.server.mappers.MetricTypeMapper;
import com.dreammy.server.models.MetricType;
import com.dreammy.server.services.MetricTypeService;
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
    public ResponseEntity<List<MetricTypeDto>> getAllMetricTypes() {
        List<MetricType> metricTypes = metricTypeService.getAllMetricTypes();
        return ResponseEntity.ok(metricTypeMapper.toDtoList(metricTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricTypeDto> getMetricTypeById(@PathVariable Long id) {
        MetricType metricType = metricTypeService.getMetricTypeById(id);
        return ResponseEntity.ok(metricTypeMapper.toDto(metricType));
    }

    @PostMapping("/default")
    public ResponseEntity<List<MetricTypeDto>> createDefaultMetricTypes() {
        List<MetricType> metricTypes = metricTypeService.createDefaultMetricTypes();
        return new ResponseEntity<>(metricTypeMapper.toDtoList(metricTypes), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricTypeDto> updateMetricType(@PathVariable Long id, @RequestBody MetricTypeDto updatedMetricTypeDto) {
        MetricType updatedResult = metricTypeService.updateMetricType(id, metricTypeMapper.toModel(updatedMetricTypeDto));
        return ResponseEntity.ok(metricTypeMapper.toDto(updatedResult));
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllMetricTypes() {
        metricTypeService.deleteAllMetricTypes();
    }
}
