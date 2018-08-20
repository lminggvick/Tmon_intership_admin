package com.tmoncorp.admin.factory;

import com.tmoncorp.admin.model.type.BaseTypeGroup;
import com.tmoncorp.admin.model.type.DataType;
import com.tmoncorp.admin.model.type.EnumMapperType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TypeModelFactory {

    @Autowired
    private TypeModelCreator<BaseTypeGroup> baseTypeCreator;

    @Autowired
    private TypeModelCreator<DataType> dataTypeCreator;

    public <E extends EnumMapperType> TypeModelCreator<E> getTypeModelCreator(String type) {
        if (StringUtils.isEmpty(type)) {
            return (TypeModelCreator<E>) baseTypeCreator;
        }
        else {
            return (TypeModelCreator<E>) dataTypeCreator;
        }
    }
}
