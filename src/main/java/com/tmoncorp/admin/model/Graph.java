package com.tmoncorp.admin.model;

import com.tmoncorp.admin.model.type.BaseType;
import com.tmoncorp.admin.model.type.DataType;
import com.tmoncorp.admin.model.type.GraphTypeGroup;
import com.tmoncorp.core.model.BaseModelSupport;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Graph<G extends GraphModel> extends BaseModelSupport {
    private int dashboardId;
    private int graphId;
    private int apiId;
    private String graphName;
    private String graphDescription;
    private GraphTypeGroup graphType;
    private BaseType baseType;
    private DataType dataType;
    private int graphUpdateCycle;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<G> graphDataList;      //XyGraph or CircleGraph

    public List<G> getGraphDataList() {
        return graphDataList;
    }

    public void setGraphDataList(List<G> graphDataList) {
        this.graphDataList = graphDataList;
    }

}
