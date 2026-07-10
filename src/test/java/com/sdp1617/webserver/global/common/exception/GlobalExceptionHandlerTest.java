package com.sdp1617.webserver.global.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.ExceptionResult;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void httpMessageNotReadableException_reports_offending_field_and_value() {
        InvalidFormatException ife = InvalidFormatException.from(null, "bad enum value", "TECHH", Department.class);
        ife.prependPath(SampleRequest.class, "department");
        HttpMessageNotReadableException e = new HttpMessageNotReadableException("parse error", ife, null);

        ResponseEntity<ErrorResponse<List<ExceptionResult.ParameterData>>> response =
                handler.handleHttpMessageNotReadableException(e);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getCode()).isEqualTo("COMMON400_BODY");
        assertThat(response.getBody().getResult()).hasSize(1);
        assertThat(response.getBody().getResult().get(0).getKey()).isEqualTo("department");
        assertThat(response.getBody().getResult().get(0).getValue()).isEqualTo("TECHH");
    }

    @Test
    void httpMessageNotReadableException_without_field_info_still_returns_400() {
        HttpMessageNotReadableException e = new HttpMessageNotReadableException("malformed json", (Throwable) null, null);

        ResponseEntity<ErrorResponse<List<ExceptionResult.ParameterData>>> response =
                handler.handleHttpMessageNotReadableException(e);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getCode()).isEqualTo("COMMON400_BODY");
        assertThat(response.getBody().getResult()).isEmpty();
    }

    @Test
    void missingServletRequestParameterException_reports_parameter_name() {
        MissingServletRequestParameterException e =
                new MissingServletRequestParameterException("department", "String");

        ResponseEntity<ErrorResponse<List<ExceptionResult.ParameterData>>> response =
                handler.handleMissingServletRequestParameterException(e);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getCode()).isEqualTo("COMMON400_PARAM");
        assertThat(response.getBody().getResult().get(0).getKey()).isEqualTo("department");
    }

    private enum Department { RESEARCH, DESIGN, TECH }

    private static class SampleRequest {}
}
