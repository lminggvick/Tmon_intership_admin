package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import com.tmoncorp.core.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectDeleteFailureException extends ApiException {
    public static final Logger logger = LoggerFactory.getLogger(ObjectDeleteFailureException.class);
    public ObjectDeleteFailureException(String message) {
        super(WettyExceptionConstants.OBJECT_DELETE_FAIL.getMessage() + "(" + message + ")",
                WettyExceptionConstants.OBJECT_DELETE_FAIL.getCode()
        );
        logger.debug("\n\n ---> [ERROR] message : " + message);
    }
}
