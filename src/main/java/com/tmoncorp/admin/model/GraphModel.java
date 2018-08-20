package com.tmoncorp.admin.model;

import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * XyGraph와 CircleGraph를 추상화한 상위 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class GraphModel extends BaseModelSupport {
    private int graphDataId;
    private int graphId;

    private LocalDateTime createDate;

}
