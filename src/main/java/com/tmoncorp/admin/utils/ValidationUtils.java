package com.tmoncorp.admin.utils;

import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.model.type.BaseSubType;
import com.tmoncorp.admin.model.type.BaseTypeCategory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtils {
    public static void checkIdParameter(int idParameter) {
        if(String.valueOf(idParameter).isEmpty()) {
            /*throw new InvalidParameterException("Id 는 '공백' 이 올 수 없습니다.");*/
        }

        if (idParameter <= 0) {
            /*throw new InvalidParameterException("Id 는 반드시 '양수' 이어야 합니다.");*/
        }
    }

    public static BaseTypeCategory InvalidSplitTimeConverter(BaseTypeCategory splitTimeType) {
        BaseSubType baseSubType = BaseSubType.create(splitTimeType);
        int remainder = Integer.parseInt(baseSubType.getValue()) % 3600;
        if (remainder != 0) {
            int quotient = Integer.parseInt(baseSubType.getValue()) / 3600;
            String validSplitTime = Integer.toString((quotient + 1) * 3600);
            splitTimeType.setCategoryValue(validSplitTime);
        }
        return splitTimeType;
    }

    public static boolean isInValidDateParameter(List<BaseTypeCategory> baseTypeCategoryList) {
        Map<String, String> dateBaseTypeCategoryMap = new HashMap<>();

        for(BaseTypeCategory baseTypeCategory : baseTypeCategoryList) {
            BaseSubType dateBaseSubType = BaseSubType.create(baseTypeCategory);
            dateBaseTypeCategoryMap.put(dateBaseSubType.getCode(), dateBaseSubType.getValue());
        }

        LocalDateTime startDate = LocalDateTime.parse(dateBaseTypeCategoryMap.get("START_DATE"), LocalDateTimeConstants.DATE_TIME_FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(dateBaseTypeCategoryMap.get("END_DATE"), LocalDateTimeConstants.DATE_TIME_FORMATTER);

        if(startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            return true;
        }
        return false;
    }
}
