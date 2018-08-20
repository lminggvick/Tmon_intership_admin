package com.tmoncorp.admin.exception;

import com.tmoncorp.core.exception.ApiException;

public class ProxyException extends ApiException {

    public ProxyException(String message, String code) {
        super(message, code);
    }

}
