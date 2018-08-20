package com.tmoncorp.admin.utils;

public enum WettyExceptionConstants {

    INVALID_PARAM("100401", "파라미터가 유효하지 않은 데이터입니다. "),
    DUPLICATE_DASHBOARD_NAME("100402", "대시보드 이름이 이미 존재합니다. "),
    OBJECT_NOT_FOUND("100500", "요청 객체를 찾을 수 없습니다. "),
    FINAL_OBJECT_CREATE_FAIL("100501", "[서버 LOG] 상수 클래스는 인스턴스 생성하지말자"),
    OBJECT_CREATE_FAIL("100502", "오브젝트 생성 실패 "),
    OBJECT_DELETE_FAIL("100503", "오브젝트 삭제 실패 "),
    OBJECT_UPDATE_FAIL("100504", "오브젝트 수정 실패 ");

    private String code;
    private String message;

    WettyExceptionConstants(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
