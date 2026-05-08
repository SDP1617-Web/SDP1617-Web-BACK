package com.sdp1617.webserver.domain.admin.project.application.dto.request;

import com.sdp1617.webserver.domain.admin.project.domain.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProjectUpdateRequest(
        @NotBlank
        String name,
        @NotBlank
        String summary,
        String description,
        String thumbnailUrl,
        String githubUrl,
        String techStack,
        @NotNull
        ProjectStatus status,
        LocalDate startDate,
        LocalDate endDate
) {
    public ProjectUpdateRequest {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be on or before endDate");
        }
    }
}
