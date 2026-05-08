package com.sdp1617.webserver.domain.admin.project.domain;

import com.sdp1617.webserver.global.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String thumbnailUrl;

    @Column(length = 255)
    private String githubUrl;

    @Column(columnDefinition = "TEXT")
    private String techStack;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    public Project(String name, String summary, String description, String thumbnailUrl,
                   String githubUrl, String techStack, ProjectStatus status,
                   LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.githubUrl = githubUrl;
        this.techStack = techStack;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String name, String summary, String description, String thumbnailUrl,
                       String githubUrl, String techStack, ProjectStatus status,
                       LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.githubUrl = githubUrl;
        this.techStack = techStack;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
