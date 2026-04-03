package com.sdp1617.webserver.global.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
