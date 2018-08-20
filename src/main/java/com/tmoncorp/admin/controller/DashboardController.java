package com.tmoncorp.admin.controller;


import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.model.Dashboard;
import com.tmoncorp.admin.model.Graph;
import com.tmoncorp.admin.model.type.BaseType;
import com.tmoncorp.admin.model.type.BaseTypeGroup;
import com.tmoncorp.admin.model.type.DataType;
import com.tmoncorp.admin.model.type.EnumMapperType;
import com.tmoncorp.admin.service.DashboardService;
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
 * Wetty API Response 규칙 정의
 * GET      - 조회한 데이터 반환
 * POST     - 오브젝트를 받아 저장하고, 성공한 경우 200(OK) + 저장된 오브젝트 반환 (DB에 저장한 경우 Entity의 PK를 포함한 오브젝트 반환)
 * PUT      - 수정된 오브젝트 반환 (클라이언트에서 오브젝트의 전체 데이터를 수정한다. 일부X)
 * DELETE   - 삭제 성공 여부 true/false 반환
 * <p>
 * TODO
 * 1. BindingResult 사용하여 유효성 검사
 * 2. 리턴 타입 수정
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends BaseAdminController {
    public static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private DashboardService dashboardService;

    // Dashboard
    @RequestMapping(value = "/management", method = RequestMethod.POST)
    @APIDescription(title = "대시보드 생성", description = "Dashboard를 생성하고, 생성된 Dashboard 정보를 반환한다")
    public Dashboard createDashboard(@RequestBody Dashboard dashboard) {
        ValidationUtils.checkIdParameter(dashboard.getApiId());
        dashboardService.createDashboard(dashboard);
        return dashboard;
    }

    @RequestMapping(value = "/management/{dashboardId}", method = RequestMethod.DELETE)
    @APIDescription(title = "대시보드 삭제", description = "Dashboard와 해당 Dashboard에 그래프 연결 정보를 삭제한다")
    public String deleteDashboard(@PathVariable int dashboardId) {
        ValidationUtils.checkIdParameter(dashboardId);
        Dashboard deletedDashboard = dashboardService.deleteDashboard(dashboardId);
        return "# 대시보드 ('" + deletedDashboard.getDashboardName() + "') 가 삭제되었습니다.";
    }

    @RequestMapping(value = "/management", method = RequestMethod.PUT)
    @APIDescription(title = "대시보드 정보 수정", description = "Dashboard를 수정하고, 수정된 Dashboard 정보를 반환한다")
    public Dashboard updateDashboard(@RequestBody @Valid Dashboard dashboard, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidParameterException(bindingResult.getFieldError().getDefaultMessage());
        }
        dashboardService.updateDashboard(dashboard);
        return dashboard;
    }

    // Graph
    // TODO : List 형태의 파라미터인 경우에 Validation 을 어떻게 처리할 것인지 고려해야한다.
    @RequestMapping(value = "/management/graph", method = RequestMethod.POST)
    @APIDescription(title = "그래프 추가", description = "그래프 리스트 추가")
    public List<Graph> createGraph(@RequestBody List<Graph> graphList) {
        if (graphList.size() == 0) {
            return graphList;
        }
        logger.debug("createGraph__" + graphList);
        dashboardService.createGraph(graphList);
        return graphList;
    }

    @RequestMapping(value = "/management/graph/{graphId}", method = RequestMethod.DELETE)
    @APIDescription(title = "그래프 삭제", description = "삭제하고자 하는 그래프 하나를 삭제한다")
    public String deleteGraph(@PathVariable int graphId) {
        ValidationUtils.checkIdParameter(graphId);
        Graph deletedGraph = dashboardService.deleteGraph(graphId);
        return "# 그래프 ('" + deletedGraph.getGraphName() + "') 가 삭제되었습니다.";
    }

    @RequestMapping(value = "/management/graph/axis", method = RequestMethod.GET)
    @APIDescription(title = "그래프 데이터 타입 조회", description = "사용하는 API 에 대해 표기가능한 타입 정보를 조회한다")
    public <E extends EnumMapperType> List<E> getTypeList(@RequestParam(value = "type", required = false) String type,
                                                          @RequestParam(value = "apiId") int apiId) {
        ValidationUtils.checkIdParameter(apiId);
        BaseType bt = new BaseType();
        bt.setCode(type);


        if(StringUtils.isNotEmpty(type) && BaseTypeGroup.UNKOWN.equals(BaseTypeGroup.create(new BaseType(type)))) {
            throw new InvalidParameterException("type('" + type + "')에 해당하는 BaseTypeCode 가 존재하지 않습니다.");
        }

        return dashboardService.getTypeList(type, apiId);
    }

    @RequestMapping(value = "/management/graph/type", method = RequestMethod.GET)
    @APIDescription(title = "그래프 타입 조회", description = "BaseType, DataType 으로 표기 가능한 그래프 유형을 조회한다")
    public Object getGraphTypeList(@RequestParam(value = "baseType") String baseTypeCode,
                                   @RequestParam(value = "dataType") String dataTypeCode) {
        if(StringUtils.isEmpty(baseTypeCode) || StringUtils.isEmpty(dataTypeCode)) {
            throw new InvalidParameterException("요청 TypeCode 중 입력 공백 값이 존재합니다.");
        }

        BaseType bt = new BaseType(baseTypeCode);

        if(BaseTypeGroup.UNKOWN.equals(BaseTypeGroup.create(bt))) {
            throw new InvalidParameterException("type('" + baseTypeCode + "')에 해당하는 BaseTypeCode 가 존재하지 않습니다.");
        }

        if(DataType.UNKNOWN.equals(DataType.create(dataTypeCode))) {
            throw new InvalidParameterException("type('" + dataTypeCode + "')에 해당하는 DataTypeCode 가 존재하지 않습니다.");
        }

        return dashboardService.getGraphTypeList(baseTypeCode, dataTypeCode);
    }

/*    @RequestMapping(value = "management/graph", method = RequestMethod.PUT)
    @APIDescription(title = "그래프 수정", description = "대시보드 내 그래프의 이름, 설명, 타입, 그래프 유형 등을 수정한다")
    public List<Graph> updateGraph(@RequestBody List<Graph> graphList) {
        dashboardService.updateGraph(graphList);
        return graphList;
    }*/
}
