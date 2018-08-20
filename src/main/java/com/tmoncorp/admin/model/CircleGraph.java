package com.tmoncorp.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CircleGraph extends GraphModel {
    private String pieName;
    private int count;

    public CircleGraph(int graphModelId, int graphId, LocalDateTime createDate, String pieName, int count) {
        super(graphModelId, graphId, createDate);
        this.pieName = pieName;
        this.count = count;
    }

}
