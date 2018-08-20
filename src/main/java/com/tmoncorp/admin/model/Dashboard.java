package com.tmoncorp.admin.model;


import com.tmoncorp.core.model.BaseModelSupport;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Dashboard extends BaseModelSupport {
    @Min(value = 1, message = "대시보드 ID는 필수 항목입니다.")
    private int dashboardId;
    @NotNull(message = "대시보드 이름을 입력하세요.")
    @Size(min = 2, max = 20, message = "대시보드 이름은 2자 이상 20자 이하여야 합니다.")
    private String dashboardName;
    private String dashboardDescription;
    @NotNull(message = "사용할 API 에 대한 ID 값은 필수 항목입니다.")
    @Min(value = 1, message = "API ID 는 1자 이상이어야 합니다.")
    @Max(value = 11, message = "API ID 는 11자 이하여야 합니다.")
    private int apiId;
    private List<DashboardGraphCollection> graphCollectionList;
}
