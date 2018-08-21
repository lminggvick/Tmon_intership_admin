package com.tmoncorp.core.test;


import com.tmoncorp.admin.exception.GlobalExceptionHandler;
import com.tmoncorp.admin.exception.InvalidParameterException;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class mockMvcUtils {
    public static HttpServletResponse doPerform(MockMvc mockMvc, RequestBuilder requestBuilder) {
        try {
            return mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse();
        } catch (Exception e) {
            throw new InvalidParameterException("");
        }
    }

    public static HttpServletResponse doPerformByRequestBody(MockMvc mockMvc, MockHttpServletRequestBuilder msr, String jsonData) {
        try {
            return mockMvc.perform(msr.content(jsonData)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse();
        } catch (Exception e) {
            throw new InvalidParameterException("");
        }
    }

/*    public static HandlerExceptionResolver initGlobalExceptionHandlerResolvers() {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerSingleton("exceptionHandler", GlobalExceptionHandler.class);

        WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        return webMvcConfigurationSupport.handlerExceptionResolver();
    }

    public static HttpServletResponse doPerform(MockMvc mockMvc, RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    public static HttpServletResponse doPerformByRequestBody(MockMvc mockMvc, MockHttpServletRequestBuilder msr, String jsonData) throws Exception {
        return mockMvc.perform(msr.content(jsonData)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    public static Exception getExceptionByPerform(MockMvc mockMvc, MockHttpServletRequestBuilder msr, String jsonData) throws Exception {
        return mockMvc.perform(msr.content(jsonData)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn()
                .getResolvedException();
    }

    public static MvcResult test(MockMvc mockMvc, MockHttpServletRequestBuilder msr, String jsonData) throws Exception {
        return mockMvc.perform(msr.content(jsonData)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn();
    }*/
}
