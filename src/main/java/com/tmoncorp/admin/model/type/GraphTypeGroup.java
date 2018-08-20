package com.tmoncorp.admin.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GraphTypeGroup implements EnumMapperUpperType<GraphSubType> {
    XY_GRAPH("XY 그래프", Arrays.asList(GraphSubType.BAR_GRAPH, GraphSubType.LINEAR_GRAPH)),
    CIRCLE_GRAPH("CIRCLE 그래프", Arrays.asList(GraphSubType.PIE_GRAPH)),
    UNKNOWN("Mismatch GraphTypeGroup", Collections.emptyList());

    private String title;
    private List<GraphSubType> categories;

    GraphTypeGroup(String title) {
        this.title = title;
    }

    GraphTypeGroup(String title, List<GraphSubType> categories) {
        this.title = title;
        this.categories = categories;
    }

    /**
     *
     * @param inputGraphSubType SubType을 input으로 받으면, 속해있는 Group을 찾고 SubType과 함께 리턴해준다.
     * @return
     */
    @JsonCreator
    public static GraphTypeGroup create(String inputGraphSubType) {
        for (GraphTypeGroup group : values()) {
            if (group.getCategories().contains(GraphSubType.valueOf(inputGraphSubType))) {
                group.categories = Collections.singletonList(GraphSubType.valueOf(inputGraphSubType));
                return group;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public List<GraphSubType> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + name() + '\'' +
                ", title='" + title + '\'' +
                ", categories=" + categories +
                '}';
    }
}
