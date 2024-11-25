package com.dreammy.server.contollers;

import com.dreammy.server.requests.metricType.MetricTypeUpdateRequest;
import com.dreammy.server.responses.MetricTypeResponse;
import com.dreammy.server.mappers.MetricTypeMapper;
import com.dreammy.server.models.MetricType;
import com.dreammy.server.services.MetricTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrictypes")
public class MetricTypeController {

    private final MetricTypeService metricTypeService;
    private final MetricTypeMapper metricTypeMapper;

    public MetricTypeController(MetricTypeService metricTypeService, MetricTypeMapper metricTypeMapper) {
        this.metricTypeService = metricTypeService;
        this.metricTypeMapper = metricTypeMapper;
    }

    @GetMapping()
    public ResponseEntity<List<MetricTypeResponse>> getAllMetricTypes() {
        List<MetricType> metricTypes = metricTypeService.getAllMetricTypes();
        return ResponseEntity.ok(metricTypeMapper.toResposeList(metricTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricTypeResponse> getMetricTypeById(@PathVariable Long id) {
        MetricType metricType = metricTypeService.getMetricTypeById(id);
        return ResponseEntity.ok(metricTypeMapper.toResponse(metricType));
    }

    @PostMapping("/default")
    public ResponseEntity<List<MetricTypeResponse>> createDefaultMetricTypes() {
        List<MetricType> metricTypes = metricTypeService.createDefaultMetricTypes();
        return new ResponseEntity<>(metricTypeMapper.toResposeList(metricTypes), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricTypeResponse> updateMetricType(@PathVariable Long id, @RequestBody MetricTypeUpdateRequest request) {
        MetricType updatedResult = metricTypeService.updateMetricType(id, metricTypeMapper.toModel(request));
        return ResponseEntity.ok(metricTypeMapper.toResponse(updatedResult));
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllMetricTypes() {
        metricTypeService.deleteAllMetricTypes();
    }
}
