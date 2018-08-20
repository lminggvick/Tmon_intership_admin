package com.tmoncorp.admin.factory;

import com.tmoncorp.admin.model.TypeCollection;
import com.tmoncorp.admin.model.type.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Get DataTypeList Logic
 */
@Component
public class DataTypeCreator implements TypeModelCreator<DataType> {
    public static final Logger logger = LoggerFactory.getLogger(DataTypeCreator.class);

    private List<DataType> dataTypeList;

    @Override
    public List<DataType> getTypeList(List<TypeCollection> typeCollectionList, Object... obj) {
        dataTypeList = new ArrayList<>();
        for(TypeCollection typeCollection : typeCollectionList) {
            if (typeCollection.getApiId() == (int)obj[0] && typeCollection.getBaseTypeGroup().name().equals(obj[1])) {
                dataTypeList.add(typeCollection.getDataType());
            }
        }
        return dataTypeList;
    }
}
