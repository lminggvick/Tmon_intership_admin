package com.tmoncorp.admin.repository;


import com.tmoncorp.core.repository.TmonRepositorySupport;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GraphRepository extends TmonRepositorySupport {

    @Autowired
    @Qualifier(value = "internshipSqlSessionFactory")
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

    @Override
    protected String getSqlMapperPrefix() {
        return "com.tmoncorp.service.graph-sqlmap.";
    }

}
