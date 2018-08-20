package com.tmoncorp.core.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoncorp.admin.controller.DashboardController;
import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.admin.model.Graph;
import com.tmoncorp.admin.model.type.EnumMapperType;
import com.tmoncorp.admin.service.DashboardService;
import com.tmoncorp.core.test.mockMvcUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {
    public static final Dashboard mockDashboard = new Dashboard(3, "name", "desc", 3, null);

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    private MockMvc mockMvc;

    private List<Graph> mockGraphList;

    /**
     * Mock Setup
     */
    @Before
    public void setup() {
        mockGraphList = new ArrayList();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }

    @Test
    public void cudDashboardRequest() throws Exception {
        /**
         *  When-Then
         */
        when(dashboardService.createDashboard(mockDashboard)).thenReturn(mockDashboard);
        when(dashboardService.updateDashboard(mockDashboard)).thenReturn(true);
        when(dashboardService.deleteDashboard(3)).thenReturn(mockDashboard);

        /**
         * Call and Assert
         */
        assertNotNull(mockMvcUtils.doPerformByRequestBody(mockMvc, post("/dashboard/management"),
                new ObjectMapper().writeValueAsString(mockDashboard)));

        assertNotNull(mockMvcUtils.doPerformByRequestBody(mockMvc, put("/dashboard/management/"),
                new ObjectMapper().writeValueAsString(mockDashboard)));

        assertNotNull(mockMvcUtils.doPerform(mockMvc, delete("/dashboard/management/{dashboardId}", 3)));

        /**
         * Verify
         */
        verify(dashboardService, times(1)).createDashboard(mockDashboard);
        verify(dashboardService, times(1)).updateDashboard(mockDashboard);
        verify(dashboardService, times(1)).deleteDashboard(3);
    }

    @Test
    public void cdGraphRequest() throws Exception {
        mockGraphList.add(new Graph());
        /**
         * When-Then
         */
        when(dashboardService.createGraph(mockGraphList)).thenReturn(true);
        when(dashboardService.deleteGraph(1)).thenReturn(new Graph());

        /**
         * Call and Assert
         */
        assertNotNull(mockMvcUtils.doPerformByRequestBody(mockMvc, post("/dashboard/management/graph"),
                new ObjectMapper().writeValueAsString(mockGraphList)));

        assertNotNull(mockMvcUtils.doPerform(mockMvc, delete("/dashboard/management/graph/{graphId}",1)));

        /**
         * Verify
         */
        verify(dashboardService, times(1)).createGraph(mockGraphList);
        verify(dashboardService, times(1)).deleteGraph(anyInt());
    }

    @Test
    public <E extends EnumMapperType> void getTypeRequest()  throws Exception {
        /**
         * When-Then
         */
        when(dashboardService.getTypeList(null, 1)).thenReturn(new ArrayList<>());
        when(dashboardService.getTypeList("DATE", 1)).thenReturn(new ArrayList<>());

        /**
         * Call and Assert
         */
        assertNotNull(mockMvcUtils.doPerformByRequestBody(mockMvc, get("/dashboard/management/graph/axis?apiId=1"),
                new ObjectMapper().writeValueAsString(mockGraphList)));

        assertNotNull(mockMvcUtils.doPerformByRequestBody(mockMvc, get("/dashboard/management/graph/axis?type=DATE&apiId=1"),
                new ObjectMapper().writeValueAsString(mockGraphList)));

        /**
         * Verify
         */
        verify(dashboardService, times(1)).getTypeList(null, 1);
        verify(dashboardService, times(1)).getTypeList("DATE", 1);
    }
}
