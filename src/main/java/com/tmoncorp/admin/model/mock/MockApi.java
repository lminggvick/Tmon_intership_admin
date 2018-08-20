package com.tmoncorp.admin.model.mock;


import com.tmoncorp.core.model.BaseModelSupport;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MockApi extends BaseModelSupport {
    @Min(value = 1, message = "apiId 는 필수 입력 항목입니다.")
    private int apiId;
    @NotNull(message = "API 이름을 입력하세요.")
    @Size(min = 1, max = 45, message = "API 이름은 1자 이상 45자 이하여야 합니다.")
    private String apiName;
    private String requestUrl;
    private String httpMethod;
    private String returnType;
    private String defaultDescription;
    @Size(min = 1, max = 45, message = "API 설명은 1자 이상 45자 이하여야 합니다.")
    private String additionalDescription;
    private char isUsedApi;

    public MockApi(int apiId, String apiName, String additionalDescription, char isUsedApi) {
        this.apiId = apiId;
        this.apiName = apiName;
        this.additionalDescription = additionalDescription;
        this.isUsedApi = isUsedApi;
    }

    public MockApi(int apiId, String apiName, String addtionalDescription, String defaultDescription) {
        this.apiId = apiId;
        this.apiName = apiName;
        this.additionalDescription = addtionalDescription;
        this.defaultDescription = defaultDescription;
    }
}
