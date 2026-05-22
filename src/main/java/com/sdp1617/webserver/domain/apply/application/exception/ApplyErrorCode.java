package com.sdp1617.webserver.domain.apply.application.exception;

import com.sdp1617.webserver.global.common.exception.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplyErrorCode implements ApiErrorCode {

    ALREADY_APPLIED("APPLICATION409", "이미 해당 모집에 지원하셨습니다.", HttpStatus.CONFLICT),
    QUESTION_NOT_FOUND("APPLICATION404_Q", "존재하지 않는 문항이 포함되어 있습니다.", HttpStatus.NOT_FOUND),
    INVALID_QUESTION("APPLICATION400_Q", "해당 모집 공고의 문항이 아닙니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_QUESTION("APPLICATION400_M", "필수 문항에 대한 답변이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_QUESTION("APPLICATION400_D", "중복된 문항 ID가 포함되어 있습니다.", HttpStatus.BAD_REQUEST),
    TECH_ROLE_REQUIRED("APPLICATION400_T", "테크팀 지원 시 세부 직무(프론트엔드/백엔드)를 선택해야 합니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_INTERVIEW_SLOT("APPLICATION400_IS_D", "중복된 면접 슬롯 ID가 포함되어 있습니다.", HttpStatus.BAD_REQUEST),
    INVALID_INTERVIEW_SLOT("APPLICATION400_IS", "유효하지 않은 면접 슬롯이 포함되어 있습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
