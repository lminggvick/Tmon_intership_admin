package com.tmoncorp.admin.repository;


import com.tmoncorp.admin.model.mock.MockApi;
import com.tmoncorp.core.repository.TmonRepositorySupport;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDashboardRepository extends TmonRepositorySupport {
    public static final Logger logger = LoggerFactory.getLogger(AdminDashboardRepository.class);

    @Autowired
    @Qualifier(value = "internshipSqlSessionFactory")
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

    @Override
    protected String getSqlMapperPrefix() {
        return "com.tmoncorp.service.mock.";
    }

    /**
     * For Test
     *
     * @param mockApi
     */
    public int createMockApi(MockApi mockApi) {
        return insert("insertApi", mockApi);
    }

    /**
     * For Test
     *
     * @param targetPara
     */
    public int deleteMockApi(String targetPara) {
        return delete("deleteApi", targetPara);
    }

    public MockApi findByApiId(int apiId) {
        return selectOne("selectApi", apiId);
    }

    public int updateMockApi(MockApi mockApi) {
        return update("updateApi", mockApi);
    }

    public List<MockApi> getApiList(String key) {
        return selectList("selectApiList", key);
    }

    public int deleteGraphWhenNotUseMockApi(int apiId) {
        return delete("deleteGraphWhenNotUseMockApi", apiId);
    }
}
