package com.tmoncorp.admin.model.type;

import java.util.List;

public interface EnumMapperUpperType<T> extends EnumMapperType {
    List<T> getCategories();
}
