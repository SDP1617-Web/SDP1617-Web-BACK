package com.sdp1617.webserver.domain.recruitment.application.exception;

import com.sdp1617.webserver.global.common.exception.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RecruitmentErrorCode implements ApiErrorCode {

    RECRUITMENT_NOT_FOUND("RECRUITMENT404", "해당 모집 공고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RECRUITMENT_NOT_ACTIVE("RECRUITMENT400", "현재 지원 가능한 모집 공고가 아닙니다.", HttpStatus.BAD_REQUEST),
    TECH_ROLE_REQUIRED("RECRUITMENT400_T", "테크팀 지원 시 세부 직무(프론트엔드/백엔드)를 선택해야 합니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
