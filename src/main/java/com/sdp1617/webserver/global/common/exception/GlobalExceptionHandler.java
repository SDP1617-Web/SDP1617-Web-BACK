package com.sdp1617.webserver.global.common.exception;

import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.ExceptionResult;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse<Void>> handleApplicationException(ApplicationException e) {
        ApiErrorCode errorCode = e.getErrorCode();
        if (errorCode.getStatus().is4xxClientError()) {
            log.warn("ApplicationException: {}", e.getMessage());
        } else {
            log.error("ApplicationException: {}", e.getMessage(), e);
        }
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getErrorCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorResponse<Void>> handleInvalidArgumentException(InvalidArgumentException e) {
        log.warn("InvalidArgumentException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST.getErrorCode(), ErrorCode.INVALID_REQUEST.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse<Void>> handleAuthenticationException(AuthenticationException e) {
        log.error("AuthenticationException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(ErrorCode.UNAUTHORIZED.getErrorCode(), ErrorCode.UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<List<ExceptionResult.ParameterData>>> handleConstraintViolationException(
            ConstraintViolationException e) {
        List<ExceptionResult.ParameterData> errors = e.getConstraintViolations().stream()
                .map(violation -> ExceptionResult.ParameterData.builder()
                        .key(violation.getPropertyPath().toString())
                        .value(violation.getInvalidValue() == null ? "null" : violation.getInvalidValue().toString())
                        .reason(violation.getMessage())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.ok(
                        ErrorCode.PARAMETER_VALIDATION_ERROR.getErrorCode(),
                        ErrorCode.PARAMETER_VALIDATION_ERROR.getMessage(),
                        errors
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<ExceptionResult.ParameterData>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        List<ExceptionResult.ParameterData> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ExceptionResult.ParameterData.builder()
                        .key(fieldError.getField())
                        .value(fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue().toString())
                        .reason(fieldError.getDefaultMessage())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.ok(
                        ErrorCode.PARAMETER_VALIDATION_ERROR.getErrorCode(),
                        ErrorCode.PARAMETER_VALIDATION_ERROR.getMessage(),
                        errors
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Void>> handleGenericException(Exception e) {
        log.error("Unhandled exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(
                        ErrorCode.SERVER_UNTRACKED_ERROR.getErrorCode(),
                        ErrorCode.SERVER_UNTRACKED_ERROR.getMessage()
                ));
    }
}
