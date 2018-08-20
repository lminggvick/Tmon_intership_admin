package com.tmoncorp.admin.model;

import com.tmoncorp.admin.model.type.GraphSubType;
import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardGraphCollection extends BaseModelSupport {
    private long collectionId;
    private int dashboardId;
    private long graphId;
    private GraphSubType graphSubType;

    public DashboardGraphCollection(int dashboardId, long graphId, GraphSubType graphSubType) {
        this.dashboardId = dashboardId;
        this.graphId = graphId;
        this.graphSubType = graphSubType;
    }
}
