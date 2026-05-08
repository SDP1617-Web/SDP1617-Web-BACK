package com.sdp1617.webserver.domain.apply.application.dto.request;

import com.sdp1617.webserver.domain.apply.entity.Department;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ApplySubmitRequest(
        @NotBlank String name,
        @NotBlank String phone,
        @NotNull LocalDate birthDate,
        @NotBlank @Email String email,
        String university,
        String major,
        @NotNull Department department,
        @NotNull @Size(min = 1) @Valid List<AnswerRequest> answers
) {
    public record AnswerRequest(
            @NotNull Long questionId,
            @NotBlank String content
    ) {}
}
