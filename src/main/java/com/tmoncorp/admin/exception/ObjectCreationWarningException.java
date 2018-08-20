package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import com.tmoncorp.core.exception.ApiException;

public class ObjectCreationWarningException extends ApiException {
    public ObjectCreationWarningException(String s) {
        super(WettyExceptionConstants.FINAL_OBJECT_CREATE_FAIL.getMessage(),
                WettyExceptionConstants.FINAL_OBJECT_CREATE_FAIL.getCode()
        );
    }
}
