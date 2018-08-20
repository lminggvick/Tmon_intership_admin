package com.tmoncorp.admin.controller;


import com.tmoncorp.admin.exception.ProxyException;
import com.tmoncorp.core.api.exception.ApiResponseException;
import com.tmoncorp.core.api.response.ApiExceptionResponse;
import com.tmoncorp.core.api.rest.client.AbstractApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/proxy")
@RestController
public class ServiceApiProxyController extends AbstractApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ServiceApiProxyController.class);
    private static final String SERVICE_INTERNSHIP_WETTY_API_HOST = "http://wettyapi-idev.tmon.co.kr/api";

    @RequestMapping(value = "/**")
    public Object proxy(HttpServletRequest request) {
        try {
            String uri = request.getRequestURI().substring("/proxy".length());
            return get(SERVICE_INTERNSHIP_WETTY_API_HOST + uri, Object.class, request.getQueryString());
        } catch (ApiResponseException e) {
            e.printStackTrace();
            ApiExceptionResponse apiResponse = e.getResponse();
            throw new ProxyException(apiResponse.getExceptionMessage(), apiResponse.getExceptionCode());
        }
    }
}
