package com.dreammy.server.repositories;

import com.dreammy.server.models.MetricValue;
import com.dreammy.server.models.MetricValueRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricValueRelationRepository extends JpaRepository<MetricValueRelation, Long> {
    @Query("SELECT mv FROM MetricValue mv WHERE mv.id IN " +
            "(SELECT mvr.childId FROM MetricValueRelation mvr WHERE mvr.parent.id = :parentId)")
    List<MetricValue> findChildrenByParentId(@Param("parentId") Long parentId);

    void deleteAllByParentId(Long parentId);
}
