package com.sdp1617.webserver.global.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ApiErrorCode errorCode;
    private final Object data;

    public ApplicationException(final ApiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data=null;
    }

    public ApplicationException(final ApiErrorCode errorCode, String message){
        super(message);
        this.errorCode=errorCode;
        this.data=getData();
    }
}
