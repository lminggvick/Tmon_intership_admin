package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import com.tmoncorp.core.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectCreateFailureException extends ApiException {
    public static final Logger logger = LoggerFactory.getLogger(ObjectCreateFailureException.class);

    public ObjectCreateFailureException(String message) {
        super(WettyExceptionConstants.OBJECT_CREATE_FAIL.getMessage() + "(" + message + ")",
                WettyExceptionConstants.OBJECT_CREATE_FAIL.getCode()
        );
        logger.debug("\n\n ---> [ERROR] message : " + message);
    }
}
