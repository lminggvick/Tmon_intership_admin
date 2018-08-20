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
public class XyGraph extends GraphModel {
    private String dataX;
    private int dataY;

    public XyGraph(int graphModelId, int graphId, LocalDateTime createDate, String dataX, int dataY) {
        super(graphModelId, graphId, createDate);
        this.dataX = dataX;
        this.dataY = dataY;
    }

}
