package com.sdp1617.webserver.domain.apply.entity;

import com.sdp1617.webserver.domain.applicant.entity.Applicant;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "application",
        uniqueConstraints = @UniqueConstraint(columnNames = {"recruitment_id", "applicant_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @Column(columnDefinition = "TEXT")
    private String reviewerNote;

    @Builder
    public Application(Recruitment recruitment, Applicant applicant, Department department) {
        this.recruitment = recruitment;
        this.applicant = applicant;
        this.department = department;
        this.status = ApplicationStatus.PENDING;
        this.submittedAt = LocalDateTime.now();
    }
}
