package com.tmoncorp.admin.model;


import com.tmoncorp.admin.model.type.BaseTypeGroup;
import com.tmoncorp.admin.model.type.DataType;
import com.tmoncorp.admin.model.type.GraphTypeGroup;
import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCollection extends BaseModelSupport {
    private int apiId;
    private BaseTypeGroup baseTypeGroup;
    private DataType dataType;
    private GraphTypeGroup graphTypeGroup;
}
