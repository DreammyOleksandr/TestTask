package com.dreammy.server.contollers;

import com.dreammy.server.mappers.MetricValueMapper;
import com.dreammy.server.requests.metricValue.MetricValueCreateRequest;
import com.dreammy.server.requests.metricValue.MetricValueUpdateRequest;
import com.dreammy.server.models.MetricValue;
import com.dreammy.server.responses.MetricValueResponse;
import com.dreammy.server.services.MetricValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metricvalues")
public class MetricValueController {

    private final MetricValueService metricValueService;
    private final MetricValueMapper mapper;

    public MetricValueController(MetricValueService metricValueService, MetricValueMapper mapper) {
        this.metricValueService = metricValueService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<MetricValueResponse> create(@RequestBody MetricValueCreateRequest request) {
        MetricValueResponse response = metricValueService.create(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MetricValueResponse>> getAll() {
        List<MetricValue> metricValues = metricValueService.getAll();
        return metricValues == null || metricValues.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(mapper.toResposeList(metricValues));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricValueResponse> getById(@PathVariable Long id) {
        MetricValueResponse response = metricValueService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<MetricValueResponse>> getHistory(@PathVariable Long id) {
        List<MetricValue> metricValues = metricValueService.getPrevious(id);
        return metricValues == null || metricValues.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(mapper.toResposeList(metricValues));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricValueResponse> update(@PathVariable Long id, @RequestBody MetricValueUpdateRequest request) {
        MetricValue updatedMetricValue = metricValueService.update(id, request);
        return ResponseEntity.ok(mapper.toResponse(updatedMetricValue));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        metricValueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
