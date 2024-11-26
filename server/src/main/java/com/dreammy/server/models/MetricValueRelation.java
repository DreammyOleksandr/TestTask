package com.dreammy.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class MetricValueRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", nullable = false)
    @JsonBackReference
    private MetricValue parent;

    private Long childId;

    public MetricValueRelation() {}

    public MetricValueRelation(MetricValue parent, Long childId) {
        this.parent = parent;
        this.childId = childId;
    }


    public MetricValue getParent() {
        return parent;
    }

    public void setParent(MetricValue parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
}
