package com.tmoncorp.admin.exception;

import com.tmoncorp.admin.utils.WettyExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseBody
    public ResponseEntity invalidParameterException(HttpServletRequest req, Exception exception, HttpServletResponse response) throws IOException {
        System.out.println("\n\n\n --> test 1 : " + new ResponseEntity(HttpStatus.BAD_REQUEST));
        System.out.println("\n\n\n --> test 2 : " + req.getRequestURL());
        System.out.println("\n\n\n --> test 3 : " + exception.getMessage());
        System.out.println("\n\n\n --> test 4 : " + exception);
        response.setStatus(Integer.parseInt(WettyExceptionConstants.INVALID_PARAM.getCode()));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
