package com.sdp1617.webserver.domain.admin.project.application.dto.request;

import com.sdp1617.webserver.domain.admin.project.domain.ProjectStatus;

import java.time.LocalDate;

public record ProjectUpdateRequest(
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        String githubUrl,
        String techStack,
        ProjectStatus status,
        LocalDate startDate,
        LocalDate endDate
) {
}
