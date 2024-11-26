package com.dreammy.server.repositories;

import com.dreammy.server.models.MetricType;
import com.dreammy.server.models.MetricValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricValueRepository extends JpaRepository<MetricValue, Long> {

    @Query("SELECT mv FROM MetricValue mv WHERE mv.metricType = :metricType AND mv.recordedDate < :recordedDate")
    List<MetricValue> findPreviousValues(@Param("metricType") MetricType metricType, @Param("recordedDate") LocalDateTime recordedDate);
}
