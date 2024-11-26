package com.dreammy.server.services;

import com.dreammy.server.exceptions.metricType.MetricTypeNotFoundException;
import com.dreammy.server.exceptions.metricValue.MetricValueNotFoundException;
import com.dreammy.server.mappers.MetricValueMapper;
import com.dreammy.server.models.MetricType;
import com.dreammy.server.models.MetricValue;
import com.dreammy.server.models.MetricValueRelation;
import com.dreammy.server.repositories.MetricTypeRepository;
import com.dreammy.server.repositories.MetricValueRelationRepository;
import com.dreammy.server.repositories.MetricValueRepository;
import com.dreammy.server.requests.metricValue.MetricValueCreateRequest;
import com.dreammy.server.requests.metricValue.MetricValueUpdateRequest;
import com.dreammy.server.responses.MetricValueResponse;
import com.dreammy.server.services.interfaces.IMetricValueService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

@Service
public class MetricValueService implements IMetricValueService {

    private final MetricValueRepository metricValueRepository;
    private final MetricTypeRepository metricTypeRepository;
    private final MetricValueRelationRepository metricValueRelationRepository;
    private final MetricValueMapper mapper;
    private final Map<String, BiPredicate<String, MetricType>> validationMap;

    public MetricValueService(MetricValueRepository metricValueRepository,
                              MetricTypeRepository metricTypeRepository,
                              MetricValueRelationRepository metricValueRelationRepository,
                              MetricValueMapper mapper) {
        this.metricValueRepository = metricValueRepository;
        this.metricTypeRepository = metricTypeRepository;
        this.metricValueRelationRepository = metricValueRelationRepository;
        this.mapper = mapper;
        this.validationMap = initializeValidationMap();
    }

    public MetricValueResponse create(MetricValueCreateRequest request) {
        MetricType metricType = findMetricTypeById(request.getMetricTypeId());

        validateMetricValueType(request.getValue(), metricType);

        MetricValue metricValue = buildMetricValue(request, metricType);
        MetricValue savedMetricValue = metricValueRepository.save(metricValue);

        List<MetricValueRelation> relations = createRelations(request.getRelationIds(), savedMetricValue.getId());
        metricValueRelationRepository.saveAll(relations);

        List<MetricValue> relatedValues = findRelatedValues(relations);
        return setMetricValueResponse(savedMetricValue, relatedValues);
    }

    public List<MetricValue> getAll() {
        return metricValueRepository.findAll();
    }

    public MetricValueResponse getById(Long id) {
        MetricValue metricValue = findMetricValueById(id);
        List<MetricValue> relatedValues = metricValueRelationRepository.findChildrenByParentId(id);
        return setMetricValueResponse(metricValue, relatedValues);
    }

    public List<MetricValue> getPrevious(Long id) {
        MetricValue metricValue = findMetricValueById(id);
        return metricValueRepository.findPreviousValues(
                metricValue.getMetricType(),
                metricValue.getRecordedDate()
        );
    }

    @Transactional
    public MetricValue update(Long id, MetricValueUpdateRequest request) {
        MetricValue existingMetricValue = findMetricValueById(id);
        MetricType metricType = existingMetricValue.getMetricType();

        validateMetricValueType(request.getValue(), metricType);

        existingMetricValue.setMetricType(metricType);
        existingMetricValue.setValue(request.getValue());

        if (request.getRelationIds() != null && !request.getRelationIds().isEmpty()) {
            metricValueRelationRepository.deleteAllByParentId(id);
            List<MetricValueRelation> newRelations = createRelations(request.getRelationIds(), id);
            metricValueRelationRepository.saveAll(newRelations);
        }

        return metricValueRepository.save(existingMetricValue);
    }


    public void delete(Long id) {
        metricValueRepository.deleteById(id);
    }

    private MetricType findMetricTypeById(Long metricTypeId) {
        return metricTypeRepository.findById(metricTypeId).
                orElseThrow(() -> new MetricTypeNotFoundException("Metric Type not found with id: " + metricTypeId));
    }

    private MetricValue findMetricValueById(Long id) {
        MetricValue metricValue = metricValueRepository
                .findById(id)
                .orElseThrow(() -> new MetricValueNotFoundException("Metric Value not found with id: " + id));
        return metricValue;
    }

    private MetricValue buildMetricValue(MetricValueCreateRequest request, MetricType metricType) {
        MetricValue metricValue = new MetricValue();
        metricValue.setMetricType(metricType);
        metricValue.setValue(request.getValue());
        return metricValue;
    }

    private List<MetricValueRelation> createRelations(List<Long> relationIds, Long parentId) {
        if (relationIds == null || relationIds.isEmpty()) {
            return Collections.emptyList();
        }

        MetricValue parent = metricValueRepository.findById(parentId).get();

        return relationIds.stream()
                .filter(id -> !id.equals(parentId) && metricValueRepository.existsById(id))
                .map(childId -> {
                    MetricValueRelation relation = new MetricValueRelation();
                    relation.setParent(parent);
                    relation.setChildId(childId);
                    return relation;
                }).toList();
    }

    private List<MetricValue> findRelatedValues(List<MetricValueRelation> relations) {
        return relations.stream()
                .map(relation -> metricValueRepository.findById(relation.getChildId()).get())
                .filter(Objects::nonNull)
                .toList();
    }

    private MetricValueResponse setMetricValueResponse(MetricValue metricValue, List<MetricValue> relatedValues) {
        MetricValueResponse response = mapper.toResponse(metricValue);
        response.setRelatedMetricValues(relatedValues);
        return response;
    }

    private void validateMetricValueType(String value, MetricType metricType) {
        Optional.ofNullable(validationMap.get(metricType.getType()))
                .filter(validator -> validator.test(value, metricType))
                .orElseThrow(() -> new IllegalArgumentException("Invalid value for metric type: " + metricType.getType()));
    }


    private Map<String, BiPredicate<String, MetricType>> initializeValidationMap() {
        Map<String, BiPredicate<String, MetricType>> map = new HashMap<>();
        map.put("FLOAT", (value, type) -> isValidNumber(value, Float::parseFloat));
        map.put("INT", (value, type) -> isValidNumber(value, Integer::parseInt));
        map.put("BOOLEAN", (value, type) -> value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
        return map;
    }

    private boolean isValidNumber(String value, Function<String, ?> parser) {
        try {
            parser.apply(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
