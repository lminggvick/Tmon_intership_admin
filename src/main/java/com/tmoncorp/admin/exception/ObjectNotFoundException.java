package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import com.tmoncorp.core.exception.ApiException;

public class ObjectNotFoundException extends ApiException {
    public ObjectNotFoundException(String message) {
        super(WettyExceptionConstants.OBJECT_NOT_FOUND.getMessage() + "(" + message + ")",
                WettyExceptionConstants.OBJECT_NOT_FOUND.getCode()
        );
    }
}
