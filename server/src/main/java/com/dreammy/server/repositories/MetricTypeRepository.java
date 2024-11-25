package com.dreammy.server.repositories;

import com.dreammy.server.models.MetricType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricTypeRepository extends JpaRepository<MetricType, Long> {
}
