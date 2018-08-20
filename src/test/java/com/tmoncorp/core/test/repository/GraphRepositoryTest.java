package com.tmoncorp.core.test.repository;


import com.tmoncorp.admin.model.DashboardGraphCollection;
import com.tmoncorp.admin.model.Graph;
import com.tmoncorp.admin.model.type.*;
import com.tmoncorp.admin.repository.DashboardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {"classpath:spring/applicationContext-simple-bean.xml",
        "classpath:spring/applicationContext-simple-database.xml",
        "classpath:spring/applicationContext-transaction.xml"})
public class GraphRepositoryTest {
    public static final Logger logger = LoggerFactory.getLogger(GraphRepositoryTest.class);
    public static final int DATA_COUNT = 2;

    @Autowired
    private DashboardRepository dashboardRepository;

    private List<Graph> mockGraphList;

    @Before
    public void setup() {
        mockGraphList = new ArrayList<>();
        mockInitialize(mockGraphList);
    }

    @Test
    public void testEnumToJsonFormat() throws IOException {
        GraphTypeGroup graphTypeGroup = GraphTypeGroup.XY_GRAPH;
        logger.debug("GraphGroup :: " + graphTypeGroup);

        GraphTypeGroup graphTypeGroup1 = GraphTypeGroup.valueOf("CIRCLE_GRAPH");
        logger.debug("GraphGroup1 :: " + graphTypeGroup1);

        GraphSubType graphSubType = GraphSubType.BAR_GRAPH;
        logger.debug("GraphSubType :: " + graphSubType);

        EnumMapperValue enumMapperValue = new EnumMapperValue(GraphTypeGroup.XY_GRAPH);
        logger.debug("GraphGroup2 :: " + enumMapperValue);
    }

    @Test
    public void crdGraph() {
        /**
         * Create
         */
        int row = dashboardRepository.insertGraphByList(mockGraphList);
        assertThat(row, is(not(0)));

        /**
         * Get Graph Id Logic
         */
        int lastInsertId = dashboardRepository.selectLastId();

        Map<String, Integer> indexMap = new HashMap<>();
        indexMap.put("startIndex", lastInsertId);
        indexMap.put("endIndex", mockGraphList.size());

        List<Integer> addGraphIdList = dashboardRepository.selectGraphList(indexMap);

        int index = 0;
        for(Graph g : mockGraphList) {
            for(BaseTypeCategory baseTypeCategory : g.getBaseType().getCategories()) {
                baseTypeCategory.setGraphId(addGraphIdList.get(index));
            }
            g.setGraphId(addGraphIdList.get(index));
            index++;
        }

        /**
         * Read
         */
        Graph matcher = mockGraphList.iterator().next();
        Graph actual = dashboardRepository.findByGraphId(matcher.getGraphId());
        assertThat(actual.getGraphId(), is(matcher.getGraphId()));

        /**
         * Update -> 기능 보류
         */
/*        for(Graph g : addGraphList) {
            g.setGraphName("UpdateGraph");
            g.setGraphDescription("UpdateDesc");
        }

        matcher = addGraphList.iterator().next();
        dashboardRepository.updateGraph(addGraphList);
        actual = dashboardRepository.findByGraphId(matcher.getGraphId());

        assertThat(actual.getGraphName(), is(matcher.getGraphName()));*/

        /**
         * Delete
         */
        dashboardRepository.deleteGraph(matcher.getGraphId());
        actual = dashboardRepository.findByGraphId(matcher.getGraphId());
        assertNull(actual);
    }

    @Test
    public void getGraphTypeList() {
        /**
         * Mock
         */
        Map<String, String> map = new HashMap<>();
        map.put("baseTypeCode", "DATE");
        map.put("dataTypeCode", "SOLD_COUNT");

        String code = dashboardRepository.selectGraphTypeCode(map);
        assertNotNull(code);
    }

    @Test
    public void deleteGraphByList() {
        dashboardRepository.insertGraphByList(mockGraphList);
        List<DashboardGraphCollection> dashboardGraphCollectionList = new ArrayList<>();

        for(Graph g : mockGraphList) {
            GraphSubType gst = g.getGraphType().getCategories().iterator().next();
            dashboardGraphCollectionList.add(new DashboardGraphCollection(g.getDashboardId(), g.getGraphId(), gst));
        }

        logger.debug("\n\n --> dashboardGraphCollectionList ? {}", dashboardGraphCollectionList);
        dashboardRepository.insertGraphCollectionByList(dashboardGraphCollectionList);

        List<Integer> collectionGraphIdList = dashboardRepository.selectCollectionGraphIdList(mockGraphList.iterator().next().getDashboardId());
        logger.debug("\n\n --> collectionGraphIdList ? {}", collectionGraphIdList);

        for(int graphId : collectionGraphIdList) {
            dashboardRepository.deleteGraphListByDashboard(graphId);
        }
    }


    private void mockInitialize(List<Graph> graphList) {
        List<BaseTypeCategory> baseTypeCategoryList = new ArrayList<>();
        baseTypeCategoryList.add(new BaseTypeCategory("10대", "true"));
        baseTypeCategoryList.add(new BaseTypeCategory("20대", "true"));
        baseTypeCategoryList.add(new BaseTypeCategory("30대", "false"));
        baseTypeCategoryList.add(new BaseTypeCategory("40대", "false"));
        baseTypeCategoryList.add(new BaseTypeCategory("50대", "false"));

        for (int i = 0; i < DATA_COUNT; i++) {
            graphList.add(new Graph(3, 0, 1,  "", "",
                    GraphTypeGroup.XY_GRAPH, new BaseType("AGE", baseTypeCategoryList),
                    DataType.SOLD_COUNT,
                    3500, LocalDateTime.now(), LocalDateTime.now(), null
            ));

            baseTypeCategoryList.clear();
            baseTypeCategoryList.add(new BaseTypeCategory("10대", "true"));
            baseTypeCategoryList.add(new BaseTypeCategory("20대", "false"));
            baseTypeCategoryList.add(new BaseTypeCategory("30대", "false"));
            baseTypeCategoryList.add(new BaseTypeCategory("40대", "false"));
            baseTypeCategoryList.add(new BaseTypeCategory("50대", "false"));
        }
    }
}
