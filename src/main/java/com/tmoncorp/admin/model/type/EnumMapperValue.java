package com.tmoncorp.admin.model.type;

import java.util.ArrayList;
import java.util.List;

public class EnumMapperValue<T> {
    private String code;
    private String title;
    private List<T> categories = new ArrayList<>();

    public EnumMapperValue() {
    }

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.title = enumMapperType.getTitle();
    }

    public EnumMapperValue(EnumMapperUpperType<T> enumMapperUpperType) {
        this.code = enumMapperUpperType.getCode();
        this.title = enumMapperUpperType.getTitle();
        this.categories = enumMapperUpperType.getCategories();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        if (categories.isEmpty()) {
            return "{" +
                    "code='" + code + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        } else {
            return "{" +
                    "code='" + code + '\'' +
                    ", title='" + title + '\'' +
                    ", categories=" + categories +
                    '}';
        }
    }
}
