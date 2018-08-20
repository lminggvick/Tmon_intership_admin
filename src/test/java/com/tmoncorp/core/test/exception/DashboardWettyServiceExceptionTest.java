package com.tmoncorp.core.test.exception;

import com.tmoncorp.admin.exception.ObjectCreateFailureException;
import com.tmoncorp.admin.exception.ObjectDeleteFailureException;
import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.admin.model.DashboardGraphCollection;
import com.tmoncorp.admin.model.Graph;
import com.tmoncorp.admin.model.type.BaseTypeCategory;
import com.tmoncorp.admin.repository.DashboardRepository;
import com.tmoncorp.admin.service.DashboardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class DashboardWettyServiceExceptionTest {

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardService dashboardService;
    private List<Graph> mockGraphList;
    private List<DashboardGraphCollection> mockDgcList;
    private List<BaseTypeCategory> mockBtcList;

    @Before
    public void setup() {
        mockGraphList = new ArrayList<>();
        mockDgcList = new ArrayList<>();
    }

    @Test(expected = ObjectCreateFailureException.class)
    public void whenCreateDashboardIsFail() {
        /**
         * given and call
         */
        given(dashboardRepository.insertDashboard(new Dashboard())).willReturn(0);
        dashboardService.createDashboard(new Dashboard());
    }

    @Test(expected = ObjectDeleteFailureException.class)
    public void whenDeleteDashboardIsFail() {
        /**
         * given and call
         */
        given(dashboardRepository.deleteDashboard(anyInt())).willReturn(0);
        dashboardService.deleteDashboard(anyInt());
    }

    @Test(expected = ObjectCreateFailureException.class)
    public void whenCreateGraphIsFail() {
        /**
         * given and call
         */
        given(dashboardRepository.insertGraphByList(mockGraphList)).willReturn(0);
        dashboardService.createGraph(mockGraphList);
    }

    @Test(expected = ObjectDeleteFailureException.class)
    public void whenDeleteGraphIsFail() {
        /**
         * given and call
         */
        given(dashboardRepository.deleteGraph(anyInt())).willReturn(0);
        dashboardService.deleteGraph(anyInt());
    }

    @Test(expected = ObjectCreateFailureException.class)
    public void whenCreateGraphCollectionIsFail() {
        /**
         * given and call
         */
        given(dashboardRepository.insertGraphByList(mockGraphList)).willReturn(1);
        given(dashboardRepository.insertGraphCollectionByList(mockDgcList)).willReturn(0);
        dashboardService.createGraph(mockGraphList);
    }

    @Test(expected = ObjectCreateFailureException.class)
    public void whenCreateBaseTypeCategoryIsFail() {
        /**
         * given and call
         */
        given(dashboardRepository.insertGraphByList(mockGraphList)).willReturn(1);
        given(dashboardRepository.insertGraphCollectionByList(mockDgcList)).willReturn(1);
        given(dashboardRepository.insertBaseTypeCategoryByList(mockBtcList)).willReturn(0);
        dashboardService.createGraph(mockGraphList);
    }

    @Test(expected = ObjectCreateFailureException.class)
    public void whenNotFoundGraphTypeCode() {
        /**
         * given and call
         */
        given(dashboardRepository.selectGraphTypeCode(new HashMap<>())).willReturn(null);
        dashboardService.getGraphTypeList("", "");
    }

    @Test(expected = ObjectCreateFailureException.class)
    public void whenNotFoundGraphCollection() {
        /**
         * given and call
         */
        given(dashboardRepository.selectTypeCodeList(anyInt())).willReturn(null);
        dashboardService.getTypeList("", anyInt());
    }
}
