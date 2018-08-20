package com.tmoncorp.core.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoncorp.admin.controller.AdminDashboardController;
import com.tmoncorp.admin.model.mock.MockApi;
import com.tmoncorp.admin.service.AdminDashboardService;
import com.tmoncorp.core.test.mockMvcUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(MockitoJUnitRunner.class)
public class AdminDashboardControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(AdminDashboardControllerTest.class);

    @Mock
    private AdminDashboardService adminDashboardService;

    @InjectMocks
    private AdminDashboardController adminDashboardController;

    private MockMvc mockMvc;
    private MockApi testMock;
    private List<MockApi> mockList;

    /**
     * Mock Setup
     */
    @Before
    public void setup() {
        testMock = new MockApi(1, "testApi1", "desc", 'F');
        mockList = new ArrayList();

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminDashboardController).build();
    }

    @Test
    public void getMockApiList() throws Exception {
        // Mock Data
        mockList.add(testMock);

        /**
         * When-Then
         */
        when(adminDashboardService.getMockApiList(anyString())).thenReturn(mockList);

        /**
         * Call and Assert
         * Query String Value : Null
         */
        assertNotNull(mockMvcUtils.doPerform(mockMvc, get("/admin-dashboard/management/?display=")));

        /**
         * Call and Assert
         * Query String Value : "use"
         */
        assertNotNull(mockMvcUtils.doPerform(mockMvc, get("/admin-dashboard/management/?display=use")));

        /**
         * Call and Assert
         * Query String Value : "all"
         */
        assertNotNull(mockMvcUtils.doPerform(mockMvc, get("/admin-dashboard/management/?display=all")));

        /**
         * Verify
         * when Query value = all -> times(2) (Default queryValue 포함)
         */
        verify(adminDashboardService, times(1)).getMockApiList("use");
        verify(adminDashboardService, times(2)).getMockApiList("all");
    }

    @Test
    public void getMockApi() throws Exception {
        /**
         * When-Then
         */
        when(adminDashboardService.getMockApi(anyInt())).thenReturn(testMock);

        /**
         * Call and Assert
         * Path Variable : {1}
         */
        assertNotNull(mockMvcUtils.doPerform(mockMvc, get( "/admin-dashboard/management/{apiId}", 1)));

        /**
         * Verify
         */
        verify(adminDashboardService, times(1)).getMockApi(anyInt());
    }

    @Test
    public void cudUsingApi() throws Exception {
        /**
         * When-then
         */
        when(adminDashboardService.updateMockApiToUsingApi(anyObject())).thenReturn(true);

        /**
         * Call and Assert
         */
        assertNotNull(mockMvcUtils.doPerformByRequestBody(mockMvc, put("/admin-dashboard/management"),
                new ObjectMapper().writeValueAsString(testMock)));

        /**
         * Verify
         */
        verify(adminDashboardService, times(1)).updateMockApiToUsingApi(testMock);
    }
}
