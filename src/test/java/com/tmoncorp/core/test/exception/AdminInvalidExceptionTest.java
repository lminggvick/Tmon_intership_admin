package com.tmoncorp.core.test.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoncorp.admin.controller.AdminDashboardController;
import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.model.mock.MockApi;
import com.tmoncorp.core.test.mockMvcUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(MockitoJUnitRunner.class)
public class AdminInvalidExceptionTest {
    @InjectMocks
    private AdminDashboardController adminDashboardController;

    private MockMvc mockMvc;
    private MockApi testMock;

    /**
     * Mock Setup
     */
    @Before
    public void setup() {
        testMock = new MockApi(1, "testApi1", "desc", 'F');
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminDashboardController).build();
    }

    /**
     * Exception TEST
     *
     * @throws JsonProcessingException -> just expected InvalidParameterException
     */
    @Test(expected = InvalidParameterException.class)
    public void whenInvalidDisplayValue() throws JsonProcessingException {
        mockMvcUtils.doPerformByRequestBody(mockMvc, get("/admin-dashboard/management?display=INVALID"), new ObjectMapper().writeValueAsString(testMock));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenInvalidIdValue() throws JsonProcessingException {
        testMock.setApiId(-1);
        mockMvcUtils.doPerformByRequestBody(mockMvc, put("/admin-dashboard/management"), new ObjectMapper().writeValueAsString(testMock));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenInvalidIsUsedApiField() throws JsonProcessingException {
        // Just 'T' or 'F'
        testMock.setIsUsedApi('Q');
        mockMvcUtils.doPerformByRequestBody(mockMvc, put("/admin-dashboard/management"), new ObjectMapper().writeValueAsString(testMock));
    }

    @Test(expected = InvalidParameterException.class)
    public void whenApiNameIsNull() throws JsonProcessingException {
        testMock.setApiName(null);
        mockMvcUtils.doPerformByRequestBody(mockMvc, put("/admin-dashboard/management"), new ObjectMapper().writeValueAsString(testMock));
    }
}
