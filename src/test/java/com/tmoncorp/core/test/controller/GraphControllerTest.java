package com.tmoncorp.core.test.controller;


import com.tmoncorp.admin.controller.DashboardController;
import com.tmoncorp.admin.service.DashboardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class GraphControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(GraphControllerTest.class);

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    @Test
    public void getTypeList() {

    }

    @Test
    public void getGraphTypeList() {

    }
}
