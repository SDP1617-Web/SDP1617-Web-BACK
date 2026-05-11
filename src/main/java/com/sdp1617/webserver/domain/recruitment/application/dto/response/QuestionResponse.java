package com.sdp1617.webserver.domain.recruitment.application.dto.response;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.apply.entity.TechRole;
import com.sdp1617.webserver.domain.question.entity.Question;

public record QuestionResponse(
        Long id,
        String content,
        int maxLength,
        int sequence,
        Department department,
        TechRole techRole
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getContent(),
                question.getMaxLength(),
                question.getSequence(),
                question.getDepartment(),
                question.getTechRole()
        );
    }
}
