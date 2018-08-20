package com.tmoncorp.admin.model.type;

import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTypeCategory extends BaseModelSupport {
    private int graphId;
    private String categoryKey;
    private String categoryValue;

    public BaseTypeCategory(String categoryKey, String categoryValue) {
        this.categoryKey = categoryKey;
        this.categoryValue = categoryValue;
    }
}
