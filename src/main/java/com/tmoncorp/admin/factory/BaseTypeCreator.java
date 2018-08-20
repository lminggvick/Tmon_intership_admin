package com.tmoncorp.admin.factory;

import com.tmoncorp.admin.model.TypeCollection;
import com.tmoncorp.admin.model.type.BaseTypeGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Get BaseTypeList Logic
 */
@Component
public class BaseTypeCreator implements TypeModelCreator<BaseTypeGroup> {
    private List<BaseTypeGroup> baseTypeList;

    @Override
    public List<BaseTypeGroup> getTypeList(List<TypeCollection> typeCollectionList, Object ... obj) {
        baseTypeList = new ArrayList<>();
        for(TypeCollection typeCollection : typeCollectionList) {
            baseTypeList.add(typeCollection.getBaseTypeGroup());
        }
        return baseTypeList;
    }
}
