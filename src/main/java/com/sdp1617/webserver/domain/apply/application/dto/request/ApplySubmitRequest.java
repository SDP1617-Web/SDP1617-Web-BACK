package com.sdp1617.webserver.domain.apply.application.dto.request;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.apply.entity.TechRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ApplySubmitRequest(
        @NotBlank
        @Size(max = 50)
        String name,

        @NotBlank
        @Size(max = 20)
        String phone,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @Size(max = 100)
        String university,

        @Size(max = 100)
        String major,

        @NotNull
        Department department,

        TechRole techRole,

        @NotNull
        @Size(min = 1)
        @Valid
        List<AnswerRequest> answers
) {
    public record AnswerRequest(
            @NotNull Long questionId,
            @NotBlank String content
    ) {}
}
