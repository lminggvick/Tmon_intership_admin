package com.tmoncorp.admin.model;

import com.tmoncorp.core.model.BaseModelSupport;
import lombok.Builder;

@Builder
public class ApiActiveState extends BaseModelSupport {
    private int apiId;
    private int dashboardCount;
    private int graphCount;

    public ApiActiveState() {
    }

    public ApiActiveState(int apiId, int dashboardCount, int graphCount) {
        this.apiId = apiId;
        this.dashboardCount = dashboardCount;
        this.graphCount = graphCount;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public int getDashboardCount() {
        return dashboardCount;
    }

    public void setDashboardCount(int dashboardCount) {
        this.dashboardCount = dashboardCount;
    }

    public int getGraphCount() {
        return graphCount;
    }

    public void setGraphCount(int graphCount) {
        this.graphCount = graphCount;
    }
}
