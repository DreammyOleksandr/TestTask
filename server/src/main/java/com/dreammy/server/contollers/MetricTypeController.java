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
    public ResponseEntity<List<MetricTypeResponse>> getAll() {
        List<MetricType> metricTypes = metricTypeService.getAll();
        return metricTypes.isEmpty() || metricTypes == null
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(metricTypeMapper.toResposeList(metricTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricTypeResponse> getById(@PathVariable Long id) {
        MetricType metricType = metricTypeService.getById(id);
        return ResponseEntity.ok(metricTypeMapper.toResponse(metricType));
    }

    @PostMapping("/default")
    public ResponseEntity<List<MetricTypeResponse>> createDefault() {
        List<MetricType> metricTypes = metricTypeService.createDefault();
        return new ResponseEntity<>(metricTypeMapper.toResposeList(metricTypes), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricTypeResponse> update(@PathVariable Long id, @RequestBody MetricTypeUpdateRequest request) {
        MetricType updatedResult = metricTypeService.update(id, metricTypeMapper.toModel(request));
        return ResponseEntity.ok(metricTypeMapper.toResponse(updatedResult));
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        metricTypeService.deleteAll();
    }
}
