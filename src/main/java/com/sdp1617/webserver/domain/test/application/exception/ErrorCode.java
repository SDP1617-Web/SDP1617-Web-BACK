package com.sdp1617.webserver.domain.test.application.exception;

public class ErrorCode extends RuntimeException {
    public ErrorCode(String message) {
        super(message);
    }
}
