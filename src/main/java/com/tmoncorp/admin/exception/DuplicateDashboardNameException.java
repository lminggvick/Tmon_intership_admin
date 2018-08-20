package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import com.tmoncorp.core.exception.ApiException;

public class DuplicateDashboardNameException extends ApiException {
    public DuplicateDashboardNameException(String message) {
        super(WettyExceptionConstants.DUPLICATE_DASHBOARD_NAME.getMessage() + "(" + message + ")",
                WettyExceptionConstants.DUPLICATE_DASHBOARD_NAME.getCode()
        );
    }
}
