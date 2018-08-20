package com.tmoncorp.core.test.repository;


import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.admin.repository.DashboardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {"classpath:spring/applicationContext-simple-bean.xml",
        "classpath:spring/applicationContext-simple-database.xml",
        "classpath:spring/applicationContext-transaction.xml"})
public class DashboardRepositoryTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Test
    public void crudDashboard() {
        Dashboard matcher = new Dashboard(0, "testDash", "desc", 999, null);
        /**
         * Create
         */
        int row = dashboardRepository.insertDashboard(matcher);
        assertThat(row, is(not(0)));

        /**
         * Read
         */
        Dashboard actual = dashboardRepository.findByDashboardId(matcher.getDashboardId());
        assertThat(actual, is(matcher));

        /**
         * Update
         */
        matcher.setDashboardName("updateName");
        matcher.setDashboardDescription("updateDesc");

        dashboardRepository.updateDashboard(matcher);
        actual = dashboardRepository.findByDashboardId(matcher.getDashboardId());
        assertThat(actual, is(matcher));

        /**
         * Delete
         */
        dashboardRepository.deleteDashboard(matcher.getDashboardId());
        actual = dashboardRepository.findByDashboardId(matcher.getDashboardId());
        assertNull(actual);
    }
}
