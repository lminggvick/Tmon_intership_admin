package com.tmoncorp.core.test.service;


import com.tmoncorp.admin.model.TypeCollection;
import com.tmoncorp.admin.model.type.BaseTypeCategory;
import com.tmoncorp.admin.model.type.BaseTypeGroup;
import com.tmoncorp.admin.model.type.DataType;
import com.tmoncorp.admin.model.type.GraphTypeGroup;
import com.tmoncorp.admin.repository.DashboardRepository;
import com.tmoncorp.admin.service.DashboardService;
import com.tmoncorp.admin.utils.ValidationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GraphServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(GraphServiceTest.class);

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private List<TypeCollection> typeCollectionList;

    private List<BaseTypeCategory> baseTypeCategoryList;

    @Before
    public void setup() {
        baseTypeCategoryList = new ArrayList<>();
        typeCollectionList = new ArrayList();
    }

    //TODO : NP Exception 발생하는 이슈
    @Test
    public void getTypeList() {
        typeCollectionList.add(new TypeCollection(1, BaseTypeGroup.DATE, DataType.SOLD_COUNT, GraphTypeGroup.XY_GRAPH));
        typeCollectionList.add(new TypeCollection(1, BaseTypeGroup.REGION, DataType.SOLD_COUNT, GraphTypeGroup.XY_GRAPH));
        typeCollectionList.add(new TypeCollection(1, BaseTypeGroup.GENDER, DataType.SOLD_COUNT, GraphTypeGroup.XY_GRAPH));
        typeCollectionList.add(new TypeCollection(1, BaseTypeGroup.AGE, DataType.SOLD_COUNT, GraphTypeGroup.XY_GRAPH));

        /**
         * given
         */
        given(dashboardRepository.selectTypeCodeList(anyInt())).willReturn(typeCollectionList);

        /**
         * Call
         * 선택한 API ID 별로 표기 할 수 있는 BaseType
         *
         */
        List<BaseTypeCategory> actual = (ArrayList)dashboardService.getTypeList("", 1);
        logger.debug("\n\n# baseTypeList ? {}", actual);

        /**
         * Call
         * BaseType 별 DataType 리스트
         */

        for (BaseTypeGroup btg : BaseTypeGroup.values()) {
            List<DataType> dataTypeList = (ArrayList)dashboardService.getTypeList(btg.name(), 1);
            logger.debug("\n\nCode ? " + btg.name() + "\n# dataTypeList ? {}", dataTypeList);
        }

        /**
         * verify
         */
        verify(dashboardRepository, atLeastOnce()).selectTypeCodeList(anyInt());
    }

    @Test
    public void getGraphTypeList() {
        /**
         * When-Then
         */
        when(dashboardRepository.selectGraphTypeCode(anyObject())).thenReturn("XY_GRAPH.CIRCLE_GRAPH");

        /**
         * Call
         */
        List<GraphTypeGroup> list = dashboardService.getGraphTypeList("", "");
        assertNotNull(list);

        /**
         * verify
         */
        verify(dashboardRepository, atLeastOnce()).selectGraphTypeCode(anyObject());
    }

    @Test
    public void dateValidationStudy() {
        baseTypeCategoryList.add(new BaseTypeCategory(1, "START_DATE", "2012-01-01 00:00:00"));
        baseTypeCategoryList.add(new BaseTypeCategory(1, "END_DATE", "2012-01-02 00:00:00"));
        assertFalse(ValidationUtils.isInValidDateParameter(baseTypeCategoryList));
    }

    /**
     * SPLIT_TIME -> 3600, 7200, 10800 ...
     * - If Input val -> 0~ 3600, then return 3600
     * - If Input val -> 3601 ~ 7200, then return 7200
     * - ...
     */
    @Test
    public void invalidSplitTimeConverter() {
        BaseTypeCategory result = ValidationUtils.InvalidSplitTimeConverter(new BaseTypeCategory(1, "SPLIT_TIME", "3"));
        assertThat(result.getCategoryValue(), is("3600"));
    }
}

