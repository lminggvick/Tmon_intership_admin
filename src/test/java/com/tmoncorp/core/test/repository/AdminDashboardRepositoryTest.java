package com.tmoncorp.core.test.repository;


import com.tmoncorp.admin.model.mock.MockApi;
import com.tmoncorp.admin.repository.AdminDashboardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/applicationContext-simple-bean.xml",
        "classpath:spring/applicationContext-simple-database.xml",
        "classpath:spring/applicationContext-transaction.xml"})

public class AdminDashboardRepositoryTest {
    public static final Logger logger = getLogger(AdminDashboardRepositoryTest.class);

    @Autowired
    private AdminDashboardRepository adminDashboardRepository;

    /**
     * Mock Setup
     */
    @Before
    public void setup() {
        adminDashboardRepository.createMockApi(new MockApi(1000, "test1", "/test-api/", "GET", "type", "TEST", "d", 'T'));
        adminDashboardRepository.createMockApi(new MockApi(1001, "test2", "/test-api/", "GET", "type", "TEST", "d", 'T'));
        adminDashboardRepository.createMockApi(new MockApi(1002, "test3", "/test-api/", "GET", "type", "TEST", "d", 'F'));
        adminDashboardRepository.createMockApi(new MockApi(1003, "test4", "/test-api/", "GET", "type", "TEST", "d", 'F'));
    }

    @Test
    public void updateMockApiToUsingApi() {
        /**
         * Read
         */
        MockApi dbApi = adminDashboardRepository.findByApiId(1000);
        assertNotNull(dbApi);

        /**
         * Update Using Api column -> T
         */
        MockApi usingApi = new MockApi(1003, "Name", "/test-api/", "GET", "type", "TEST", "Desc", 'T');
        adminDashboardRepository.updateMockApi(usingApi);
        MockApi matcher = adminDashboardRepository.findByApiId(usingApi.getApiId());
        assertThat(usingApi.getIsUsedApi(), is(matcher.getIsUsedApi()));

        /**
         * Update Additional Description
         */
        usingApi.setAdditionalDescription("newDesc");
        adminDashboardRepository.updateMockApi(usingApi);
        matcher = adminDashboardRepository.findByApiId(usingApi.getApiId());
        assertThat(usingApi.getAdditionalDescription(), is(matcher.getAdditionalDescription()));

        /**
         * Delete Using Api column -> F
         */
        usingApi.setIsUsedApi('F');
        adminDashboardRepository.updateMockApi(usingApi);
        matcher = adminDashboardRepository.findByApiId(usingApi.getApiId());
        assertThat(usingApi.getIsUsedApi(), is(matcher.getIsUsedApi()));
    }

    @Test
    public void getMockApiList() {
        /**
         * Get All Api
         */
        logger.debug("\n\nAll API List {}", adminDashboardRepository.getApiList("all").size());
        assertNotNull(adminDashboardRepository.getApiList("all"));

        /**
         * Get Using Api
         */
        logger.debug("\n\nUsing API List {}", adminDashboardRepository.getApiList("use").size());
        assertNotNull(adminDashboardRepository.getApiList("use"));

        /**
         * Get Not Using Api
         */
        logger.debug("\n\nNot used API List {}", adminDashboardRepository.getApiList("no").size());
        assertNotNull(adminDashboardRepository.getApiList("no"));
    }
}
