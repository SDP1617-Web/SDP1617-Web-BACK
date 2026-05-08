package com.sdp1617.webserver.global.common.exception;

import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse<Void>> handleApplicationException(ApplicationException e) {
        ApiErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getErrorCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<ExceptionResult.ParameterData>>> handleValidationException(
            MethodArgumentNotValidException e) {
        List<ExceptionResult.ParameterData> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ExceptionResult.ParameterData.builder()
                        .key(fieldError.getField())
                        .value(fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue().toString())
                        .reason(fieldError.getDefaultMessage())
                        .build())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.ok(
                        ErrorCode.PARAMETER_VALIDATION_ERROR.getErrorCode(),
                        ErrorCode.PARAMETER_VALIDATION_ERROR.getMessage(),
                        errors
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<ExceptionResult.ServerErrorData>> handleException(Exception e) {
        ExceptionResult.ServerErrorData data = ExceptionResult.ServerErrorData.builder()
                .errorClass(e.getClass().getName())
                .errorMessage(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.ok(
                        ErrorCode.SERVER_UNTRACKED_ERROR.getErrorCode(),
                        ErrorCode.SERVER_UNTRACKED_ERROR.getMessage(),
                        data
                ));
    }
}
