package com.sdp1617.webserver.domain.apply.application.exception;

import com.sdp1617.webserver.global.common.exception.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplyErrorCode implements ApiErrorCode {

    RECRUITMENT_NOT_FOUND("APPLICATION404", "해당 모집 공고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RECRUITMENT_NOT_ACTIVE("APPLICATION400", "현재 지원 가능한 모집 공고가 아닙니다.", HttpStatus.BAD_REQUEST),
    ALREADY_APPLIED("APPLICATION409", "이미 해당 모집에 지원하셨습니다.", HttpStatus.CONFLICT),
    QUESTION_NOT_FOUND("APPLICATION404_Q", "존재하지 않는 문항이 포함되어 있습니다.", HttpStatus.NOT_FOUND),
    INVALID_QUESTION("APPLICATION400_Q", "해당 모집 공고의 문항이 아닙니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_QUESTION("APPLICATION400_M", "필수 문항에 대한 답변이 누락되었습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
