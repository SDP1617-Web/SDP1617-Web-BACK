package com.sdp1617.webserver.domain.recruitment.entity;

import com.sdp1617.webserver.domain.apply.application.exception.ApplicationErrorCode;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "recruitment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 20)
    private String semester;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime deadlineAt;

    @Column(nullable = false)
    private boolean isActive = true;

    @Builder
    public Recruitment(String title, String semester, LocalDateTime startAt, LocalDateTime deadlineAt, boolean isActive) {
        this.title = title;
        this.semester = semester;
        this.startAt = startAt;
        this.deadlineAt = deadlineAt;
        this.isActive = isActive;
    }

    public void validateActive() {
        LocalDateTime now = LocalDateTime.now();
        if (!isActive || now.isBefore(startAt) || now.isAfter(deadlineAt)) {
            throw new ApplicationException(ApplicationErrorCode.RECRUITMENT_NOT_ACTIVE);
        }
    }
}
