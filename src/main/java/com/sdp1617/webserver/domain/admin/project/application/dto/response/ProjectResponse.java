package com.sdp1617.webserver.domain.admin.project.application.dto.response;

import com.sdp1617.webserver.domain.admin.project.domain.Project;
import com.sdp1617.webserver.domain.admin.project.domain.ProjectStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectResponse(
        Long id,
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        String githubUrl,
        String techStack,
        ProjectStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getSummary(),
                project.getDescription(),
                project.getThumbnailUrl(),
                project.getGithubUrl(),
                project.getTechStack(),
                project.getStatus(),
                project.getStartDate(),
                project.getEndDate(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
