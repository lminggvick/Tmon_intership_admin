package com.tmoncorp.admin.repository;


import com.tmoncorp.admin.exception.DuplicateDashboardNameException;
import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.admin.model.DashboardGraphCollection;
import com.tmoncorp.admin.model.Graph;
import com.tmoncorp.admin.model.TypeCollection;
import com.tmoncorp.admin.model.type.BaseTypeCategory;
import com.tmoncorp.core.repository.TmonRepositorySupport;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DashboardRepository extends TmonRepositorySupport {
    public static final Logger logger = LoggerFactory.getLogger(DashboardRepository.class);

    @Autowired
    @Qualifier(value = "internshipSqlSessionFactory")
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

    @Override
    protected String getSqlMapperPrefix() {
        return "com.tmoncorp.service.dashboard.";
    }

    // Dashboard
    public int insertDashboard(Dashboard dashboard) {
        int row = 0;
        try {
            row = insert("insertDashboard", dashboard);
        } catch (Exception e) {
            if (e.getClass().equals(DuplicateKeyException.class)) {
                throw new DuplicateDashboardNameException(" # 중복 키 Name : " + dashboard.getDashboardName());
            }
        }
        return row;
    }

    public Dashboard findByDashboardId(int dashBoardId) {
        return selectOne("selectDashboard", dashBoardId);
    }

    public int deleteDashboard(int dashboardId) {
        return delete("deleteDashboard", dashboardId);
    }

    public int updateDashboard(Dashboard dashboard) {
        return update("updateDashboard", dashboard);
    }

    public int deleteGraphCollectionByDashboardId(int dashboardId) {
        return delete("deleteGraphCollectionByDashboardId", dashboardId);
    }

    // Graph
    public int insertGraphByList(List<Graph> graphList) {
        return insert("insertGraphByList", graphList);
    }

    public int selectLastId() {
        return selectOne("selectLastId");
    }

    public List<Integer> selectGraphList(Map<String, Integer> map) {
        return selectList("selectGraphIdList", map);
    }

    public int insertBaseTypeCategoryByList(List<BaseTypeCategory> baseTypeCategoryList) {
        return insert("insertBaseTypeCategoryByList", baseTypeCategoryList);
    }

    public int insertGraphCollectionByList(List<DashboardGraphCollection> dgcList) {
        return insert("insertGraphCollectionByList", dgcList);
    }

    public int deleteGraph(int graphId) {
        return delete("deleteGraph", graphId);
    }

    public Graph findByGraphId(int graphId) {
        return selectOne("selectGraph", graphId);
    }

    public List<TypeCollection> selectTypeCodeList(int apiId) {
        return selectList("selectTypeList", apiId);
    }

    public String selectGraphTypeCode(Map<String, String> typeMap) {
        return selectOne("selectGraphTypeCode", typeMap);
    }

    public int updateGraph(List<Graph> graphList) {
        return update("updateGraph", graphList);
    }

    public int getDashboardCountByApiId(int apiId) {
        return selectOne("getDashboardCountByApiId", apiId);
    }

    public int getGraphCountByApiId(int apiId) {
        return selectOne("getGraphCountByApiId", apiId);
    }

    public int deleteGraphCollectionByGraphId(int graphId) {
        return delete("deleteGraphCollectionByGraphId", graphId);
    }

    public int deleteBaseTypeCategory(int graphId) {
        return delete("deleteBaseTypeCategory", graphId);
    }

    public List<Integer> selectCollectionGraphIdList(int dashboardId) {
        return selectList("selectCollectionGraphIdList", dashboardId);
    }

    public int deleteGraphListByDashboard(int graphId) {
        return delete("deleteGraphByList", graphId);
    }
}
