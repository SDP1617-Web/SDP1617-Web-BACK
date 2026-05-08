package com.sdp1617.webserver.domain.portfolio.application.exception;

import com.sdp1617.webserver.global.common.exception.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PortfolioErrorCode implements ApiErrorCode {

    APPLICATION_NOT_FOUND("PORTFOLIO404", "해당 지원서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PORTFOLIO_ALREADY_EXISTS("PORTFOLIO409", "이미 포트폴리오가 등록된 지원서입니다.", HttpStatus.CONFLICT),
    FILE_UPLOAD_FAILED("PORTFOLIO500", "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FILE_TYPE("PORTFOLIO400", "허용되지 않는 파일 형식입니다. (pdf, zip, png, jpg만 허용)", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED("PORTFOLIO400_S", "파일 크기가 초과되었습니다. (최대 10MB)", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
