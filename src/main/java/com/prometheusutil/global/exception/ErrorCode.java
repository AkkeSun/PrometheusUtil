package com.prometheusutil.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // status code 404 (2001 - 2099)
    DoesNotExist_INSTANCE(2001, "조회된 인스턴스가 없습니다."),

    ;
    
    private final int code;
    private final String message;
}
