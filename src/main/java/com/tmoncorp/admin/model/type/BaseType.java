package com.tmoncorp.admin.model.type;

import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseType extends BaseModelSupport {
    private String code;
    private List<BaseTypeCategory> categories;

    public BaseType(String code) {
        this.code = code;
    }
}
