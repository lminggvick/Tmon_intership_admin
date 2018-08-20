package com.tmoncorp.admin.exception;

import org.springframework.http.HttpStatus;

public class ApiException {
    String exceptionCode;
    Object exceptionInfo;
    boolean logable;
    private static final long serialVersionUID = -7100262123084299295L;
    private HttpStatus httpStatus;

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public void setExceptionInfo(Object exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public void setLoggable(boolean logable) {
        this.logable = logable;
    }
}
