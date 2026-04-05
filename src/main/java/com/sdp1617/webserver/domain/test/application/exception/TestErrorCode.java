package com.sdp1617.webserver.domain.test.application.exception;

import com.sdp1617.webserver.global.common.exception.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements ApiErrorCode {
    TEST_NOT_FOUND("TEST404","해당 api를 찾을 수 없어요.",HttpStatus.NOT_FOUND);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
