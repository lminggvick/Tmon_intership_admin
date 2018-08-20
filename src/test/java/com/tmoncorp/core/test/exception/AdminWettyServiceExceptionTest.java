package com.tmoncorp.core.test.exception;

import com.tmoncorp.admin.exception.ObjectNotFoundException;
import com.tmoncorp.admin.repository.AdminDashboardRepository;
import com.tmoncorp.admin.service.AdminDashboardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class AdminWettyServiceExceptionTest {

    @Mock
    private AdminDashboardRepository adminDashboardRepository;

    @InjectMocks
    private AdminDashboardService adminDashboardService;

    @Before
    public void setup() {

    }

    @Test(expected = ObjectNotFoundException.class)
    public void whenCreateDashboardIsFail() {
        /**
         * given and call
         */
        given(adminDashboardRepository.findByApiId(anyInt())).willReturn(null);
        adminDashboardService.getMockApi(anyInt());
    }
}
