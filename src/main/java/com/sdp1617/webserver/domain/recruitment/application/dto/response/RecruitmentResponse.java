package com.sdp1617.webserver.domain.recruitment.application.dto.response;

import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;

import java.time.LocalDateTime;

public record RecruitmentResponse(
        Long id,
        String title,
        String semester,
        LocalDateTime startAt,
        LocalDateTime deadlineAt
) {
    public static RecruitmentResponse from(Recruitment recruitment) {
        return new RecruitmentResponse(
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getSemester(),
                recruitment.getStartAt(),
                recruitment.getDeadlineAt()
        );
    }
}
