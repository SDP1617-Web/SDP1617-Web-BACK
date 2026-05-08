package com.sdp1617.webserver.domain.admin.project.application.dto.request;

import com.sdp1617.webserver.domain.admin.project.domain.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProjectCreateRequest(
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
    public ProjectCreateRequest {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (summary == null || summary.isBlank()) {
            throw new IllegalArgumentException("summary must not be blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be blank");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate must not be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be on or before endDate");
        }
    }
}
