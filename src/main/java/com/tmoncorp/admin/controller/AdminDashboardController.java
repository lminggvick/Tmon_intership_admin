package com.tmoncorp.admin.controller;


import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.model.ApiActiveState;
import com.tmoncorp.admin.model.mock.MockApi;
import com.tmoncorp.admin.service.AdminDashboardService;
import com.tmoncorp.admin.utils.ValidationUtils;
import com.tmoncorp.mvc.controller.BaseAdminController;
import com.tmoncorpdev.apihelper.APIDescription;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO : 컨트롤러 별로 유효값 검증 로직 분리할 필요가 있다.
 */
@RestController
@RequestMapping("/admin-dashboard")
public class AdminDashboardController extends BaseAdminController {
    public static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    @Autowired
    private AdminDashboardService adminDashboardService;

    @RequestMapping(value = "/management", method = RequestMethod.GET)
    @APIDescription(title = "Using, All API 리스트 조회", description = "display 파라미터 (all, use, no) 별 API 목록을 조회한다.")
    public List<MockApi> getMockApiList(@RequestParam(value = "display", required = false, defaultValue = "all") String key) {
        if (!(StringUtils.equals(key, "use") || StringUtils.equals(key, "no") || StringUtils.equals(key, "all"))) {
            //throw new InvalidParameterException(" display 값은 공백, all, use, no 중에 하나이어야 합니다. (DEFAULT : all) ");
            key = "all";
        }
        return adminDashboardService.getMockApiList(key);
    }

    @RequestMapping(value = "/management/{apiId}", method = RequestMethod.GET)
    @APIDescription(title = "API 에 대한 정보 조회", description = "API 목록 중 선택한 API 에 대한 정보를 조회한다.")
    public MockApi getMockApi(@PathVariable int apiId) {
        ValidationUtils.checkIdParameter(apiId);
        return adminDashboardService.getMockApi(apiId);
    }

    @RequestMapping(value = "/management", method = RequestMethod.PUT)
    @APIDescription(title = "MockApi 수정", description = "사용중(T), 사용안함(F), 사용자가 정의한 Description 이 3가지 수정 로직을 처리한다.")
    public MockApi updateMockApiToUsingApi(@RequestBody @Valid MockApi mockApi, BindingResult bindingResult) {
        ValidationUtils.checkIdParameter(mockApi.getApiId());
        if (bindingResult.hasErrors()) {
            throw new InvalidParameterException(bindingResult.getFieldError().getDefaultMessage());
        }

        if (!(mockApi.getIsUsedApi() == 'T' || mockApi.getIsUsedApi() == 'F')) {
            throw new InvalidParameterException("isUsedApi 는 T 또는 F 여야 합니다.");
        }

        adminDashboardService.updateMockApiToUsingApi(mockApi);
        return mockApi;
    }

    @RequestMapping(value = "/management/api-state", method = RequestMethod.GET)
    @APIDescription(title = "Mock API를 사용하는 대시보드와 그래프 조회", description = "특정 Mock API를 비활성화하기 전에 관련된 대시보드와 그래프가 있는지 확인하는 기능")
    public ApiActiveState getApiActiveState(@RequestParam("apiId") int apiId) {
        return adminDashboardService.getApiActiveState(apiId);
    }
}
