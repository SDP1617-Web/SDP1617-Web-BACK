package com.sdp1617.webserver.global.common.exception;

import org.springframework.http.HttpStatus;

public interface ApiErrorCode {
    String getErrorCode();
    String getMessage();
    HttpStatus getStatus();
}