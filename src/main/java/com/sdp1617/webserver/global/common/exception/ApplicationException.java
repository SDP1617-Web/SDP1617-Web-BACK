package com.sdp1617.webserver.global.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCodeInterface errorCode;
    private final Object data;

    public ApplicationException(final ErrorCodeInterface errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data=null;
    }

    public ApplicationException(final ErrorCodeInterface errorCode,String message){
        super(message);
        this.errorCode=errorCode;
        this.data=getData();
    }
}
