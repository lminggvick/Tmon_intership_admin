package com.tmoncorp.admin.service;


import com.tmoncorp.admin.exception.ObjectNotFoundException;
import com.tmoncorp.admin.model.ApiActiveState;
import com.tmoncorp.admin.model.mock.MockApi;
import com.tmoncorp.admin.repository.AdminDashboardRepository;
import com.tmoncorp.admin.repository.DashboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardService {
    public static final Logger logger = LoggerFactory.getLogger(AdminDashboardService.class);

    @Autowired
    private AdminDashboardRepository adminDashboardRepository;
    @Autowired
    private DashboardRepository dashboardRepository;

    public List<MockApi> getMockApiList(String key) {
        return adminDashboardRepository.getApiList(key);
    }

    public boolean updateMockApiToUsingApi(MockApi mockApi) {
        adminDashboardRepository.updateMockApi(mockApi);
        return true;
    }

    public MockApi getMockApi(int apiId) {
        MockApi findMockApi = adminDashboardRepository.findByApiId(apiId);
        if(findMockApi == null) {
            throw new ObjectNotFoundException("apiId('" + apiId + "') 에 대한 API 가 존재하지 않습니다.");
        }
        return findMockApi;
    }

    /**
     * api를 사용하는 대시보드와 그래프 개수를 조회
     * @param apiId
     * @return
     */
    public ApiActiveState getApiActiveState(int apiId) {
        return ApiActiveState.builder()
                .apiId(apiId)
                .dashboardCount(dashboardRepository.getDashboardCountByApiId(apiId))
                .graphCount(dashboardRepository.getGraphCountByApiId(apiId))
                .build();
    }
}
