package com.tmoncorp.core.test.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoncorp.admin.controller.DashboardController;
import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.core.test.mockMvcUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardInvalidExceptionTest {
    @InjectMocks
    private DashboardController dashboardController;

    private MockMvc mockMvc;
    private Dashboard mockDashboard;

    /**
     * Mock Setup
     */
    @Before
    public void setup() {
        mockDashboard = new Dashboard(3, "name", "desc", 3, null);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }

    /**
     * Exception TEST
     *
     * @throws JsonProcessingException -> just expected InvalidParameterException
     */
    @Test(expected = InvalidParameterException.class)
    public void whenInvalidIdPara() throws JsonProcessingException {
        mockDashboard.setApiId(-1);
        mockMvcUtils.doPerformByRequestBody(mockMvc, post("/dashboard/management"), new ObjectMapper().writeValueAsString(mockDashboard));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenDashboardNameIsNull() throws JsonProcessingException {
        mockDashboard.setDashboardName(null);
        mockMvcUtils.doPerformByRequestBody(mockMvc, put("/dashboard/management"), new ObjectMapper().writeValueAsString(mockDashboard));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenInvalidBaseTypeValue() throws JsonProcessingException {
        mockMvcUtils.doPerformByRequestBody(mockMvc, get("/dashboard/management/graph/axis?type=INVALID&apiId=1"), new ObjectMapper().writeValueAsString(mockDashboard));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenTypeCodeIsEmpty() throws JsonProcessingException {
        mockMvcUtils.doPerformByRequestBody(mockMvc, get("/dashboard/management/graph/type?baseType=&dataType=INVALID"), new ObjectMapper().writeValueAsString(mockDashboard));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenInvalidDataTypeValue() throws JsonProcessingException {
        mockMvcUtils.doPerformByRequestBody(mockMvc, get("/dashboard/management/graph/type?baseType=DATE&dataType=INVALID"), new ObjectMapper().writeValueAsString(mockDashboard));
    }
}
