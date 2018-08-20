package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import com.tmoncorp.core.exception.ApiException;

public class ObjectUpdateFailureException extends ApiException {
    public ObjectUpdateFailureException(String message) {
        super(WettyExceptionConstants.OBJECT_UPDATE_FAIL.getMessage() + "(" + message + ")",
                WettyExceptionConstants.OBJECT_UPDATE_FAIL.getCode()
        );
    }
}
