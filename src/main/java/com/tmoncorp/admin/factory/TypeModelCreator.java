package com.tmoncorp.admin.factory;

import com.tmoncorp.admin.model.TypeCollection;
import com.tmoncorp.admin.model.type.EnumMapperType;

import java.util.List;

public interface TypeModelCreator <E extends EnumMapperType>{
     List<E> getTypeList(List<TypeCollection> typeCollectionList, Object... obj);
}
