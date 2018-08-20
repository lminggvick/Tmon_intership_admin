package com.tmoncorp.admin.service;


import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.exception.ObjectCreateFailureException;
import com.tmoncorp.admin.exception.ObjectDeleteFailureException;
import com.tmoncorp.admin.factory.TypeModelCreator;
import com.tmoncorp.admin.factory.TypeModelFactory;
import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.admin.model.DashboardGraphCollection;
import com.tmoncorp.admin.model.Graph;
import com.tmoncorp.admin.model.TypeCollection;
import com.tmoncorp.admin.model.type.BaseTypeCategory;
import com.tmoncorp.admin.model.type.EnumMapperType;
import com.tmoncorp.admin.model.type.GraphSubType;
import com.tmoncorp.admin.model.type.GraphTypeGroup;
import com.tmoncorp.admin.repository.DashboardRepository;
import com.tmoncorp.admin.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class DashboardService {
    public static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private TypeModelFactory typeModelFactory;

    public Dashboard createDashboard(Dashboard dashboard) {
        if(dashboardRepository.insertDashboard(dashboard) == 0) {
            throw new ObjectCreateFailureException("Dashboard 생성에 실패했습니다. [Request Body] : " + dashboard);
        }
        return dashboard;
    }

    @Transactional
    public Dashboard deleteDashboard(int dashboardId) {
        Dashboard deletedDashboard = dashboardRepository.findByDashboardId(dashboardId);
        if(dashboardRepository.deleteDashboard(dashboardId) == 0) {
            throw new ObjectDeleteFailureException("Dashboard 삭제에 실패했습니다. [Request id] : " + dashboardId);
        }

        List<Integer> collectionGraphIdList = dashboardRepository.selectCollectionGraphIdList(dashboardId);

        for(int graphId : collectionGraphIdList) {
            dashboardRepository.deleteGraphListByDashboard(graphId);
        }

        dashboardRepository.deleteGraphCollectionByDashboardId(dashboardId);
        return deletedDashboard;
    }

    /**
     * @param dashboard
     * @return
     */
    public boolean updateDashboard(Dashboard dashboard) {
        dashboardRepository.updateDashboard(dashboard);
        return true;
    }

    @Transactional
    public boolean createGraph(List<Graph> graphList) {
        /**
         * Graph Insert Logic
         */
        if (dashboardRepository.insertGraphByList(graphList) == 0) {
            throw new ObjectCreateFailureException("Graph 생성에 실패했습니다. [Request Body] : " + graphList);
        }

        graphList = setGraphIdAndBaseTypeCategory(graphList);

        List<DashboardGraphCollection> dashboardGraphCollectionList = getDashboardGraphCollectionList(graphList);

        if (dashboardRepository.insertGraphCollectionByList(dashboardGraphCollectionList) == 0) {
            throw new ObjectCreateFailureException("GraphCollection 생성에 실패하여 그래프를 추가하지 못했습니다.");
        }

        List<BaseTypeCategory> baseTypeCategoryList = getBaseTypeCategoryList(graphList);

        if (dashboardRepository.insertBaseTypeCategoryByList(baseTypeCategoryList) == 0) {
            throw new ObjectCreateFailureException("BaseTypeCategory 생성에 실패하여 그래프를 추가하지 못했습니다.");
        }

        return true;
    }

    @Transactional
    public Graph deleteGraph(int graphId) {
        Graph deletedGraph = dashboardRepository.findByGraphId(graphId);

        if(dashboardRepository.deleteGraph(graphId) == 0) {
            throw new ObjectDeleteFailureException("graphId('" + graphId + "') 그래프 삭제에 실패하였습니다.");
        }

        if(dashboardRepository.deleteGraphCollectionByGraphId(graphId) == 0) {
            throw new ObjectDeleteFailureException("대시보드에 대한 GraphCollection 삭제에 실패하여 그래프를 삭제하지 못했습니다.");
        }

        if(dashboardRepository.deleteBaseTypeCategory(graphId) == 0) {
            throw new ObjectDeleteFailureException("그래프에 대한 baseTypeCategory 삭제에 실패하여 그래프를 삭제하지 못했습니다.");
        }

        return deletedGraph;
    }

    public List<GraphTypeGroup> getGraphTypeList(String baseTypeCode, String dataTypeCode) {
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("baseTypeCode", baseTypeCode);
        typeMap.put("dataTypeCode", dataTypeCode);

        List<GraphTypeGroup> graphTypeGroupList = new ArrayList<>();
        String graphTypeCode = dashboardRepository.selectGraphTypeCode(typeMap);

        if(StringUtils.equals(graphTypeCode, null)) {
            throw new ObjectCreateFailureException("해당 Type 조합으로는 그래프를 조합할 수 없습니다.");
        }

        String[] graphTypeCodeList = StringUtils.split(graphTypeCode, "//.");

        for (String code : graphTypeCodeList) {
            graphTypeGroupList.add(GraphTypeGroup.valueOf(code));
        }

        return graphTypeGroupList;
    }

/*    public boolean updateGraph(List<Graph> graphList) {
        dashboardRepository.updateGraph(graphList);
        return true;
    }*/

    public <E extends EnumMapperType> List<E> getTypeList(String type, int apiId) {
        List<TypeCollection> typeList = dashboardRepository.selectTypeCodeList(apiId);
        if (typeList == null) {
            throw new ObjectCreateFailureException("해당 apiId('" + apiId + "') 에 대한 TypeCollection 데이터가 존재하지 않습니다.");
        }

        TypeModelCreator<E> typeModelCreator = typeModelFactory.getTypeModelCreator(type);
        logger.debug("\n\n ---> typeModelCreator ? {}", typeModelCreator);
        return typeModelCreator.getTypeList(typeList, apiId, type);
    }

    private List<Integer> getInsertedGraphList(int lastInsertId, int len) {
        Map<String, Integer> indexMap = new HashMap<>();
        indexMap.put("startIndex", lastInsertId);
        indexMap.put("endIndex", len);
        return dashboardRepository.selectGraphList(indexMap);
    }

    private List<BaseTypeCategory> getBaseTypeCategoryList(List<Graph> graphList) {
        List<BaseTypeCategory> baseTypeCategoryList = new ArrayList<>();
        for(Graph g : graphList) {
            if(StringUtils.equals(g.getBaseType().getCode(), "DATE")) {
                if (ValidationUtils.isInValidDateParameter(g.getBaseType().getCategories())) {
                    throw new InvalidParameterException("종료 날짜는 시작 날짜보다 빠르거나 같을 수 없습니다. 그래프 ('" + g.getGraphName() + "') 을 확인해주세요.");
                }
                g.getBaseType().getCategories().set(2, ValidationUtils.InvalidSplitTimeConverter(g.getBaseType().getCategories().get(2)));
            }

            baseTypeCategoryList.addAll(g.getBaseType().getCategories());
        }
        return baseTypeCategoryList;
    }

    private List<DashboardGraphCollection> getDashboardGraphCollectionList(List<Graph> graphList) {
        List<DashboardGraphCollection> dashboardGraphCollectionList = new ArrayList<>();
        for(Graph g : graphList) {
            int dashboardId = g.getDashboardId();
            GraphSubType gst = g.getGraphType().getCategories().iterator().next();
            dashboardGraphCollectionList.add(new DashboardGraphCollection(dashboardId, g.getGraphId(), gst));
        }
        return dashboardGraphCollectionList;
    }

    private List<Graph> setGraphIdAndBaseTypeCategory(List<Graph> graphList) {
        int lastInsertId = dashboardRepository.selectLastId();
        List<Integer> addGraphIdList = getInsertedGraphList(lastInsertId, graphList.size());
        int index = 0;
        for(Graph g : graphList) {
            for(BaseTypeCategory baseTypeCategory : g.getBaseType().getCategories()) {
                baseTypeCategory.setGraphId(addGraphIdList.get(index));
            }
            g.setGraphId(addGraphIdList.get(index));
            index++;
        }
        return graphList;
    }
}
