package com.tmoncorp.admin.utils;

import com.tmoncorp.admin.exception.ObjectCreationWarningException;

import java.time.format.DateTimeFormatter;

public final class LocalDateTimeConstants {

    /**
     * 객체 생성 방지
     */
    private LocalDateTimeConstants() {
        throw new ObjectCreationWarningException(this.getClass().getName() + "은 상수 클래스입니다.");
    }

    public static final String DATE_TIME_STRING = "yyyy-MM-dd HH:00:00";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_STRING);



}
