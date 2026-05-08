package com.sdp1617.webserver.domain.recruitment.entity;

import com.sdp1617.webserver.domain.apply.application.exception.ApplyErrorCode;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "recruitment")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Recruitment extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 20)
    private String semester;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime deadlineAt;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;

    public void validateActive() {
        LocalDateTime now = LocalDateTime.now();
        if (!isActive || now.isBefore(startAt) || now.isAfter(deadlineAt)) {
            throw new ApplicationException(ApplyErrorCode.RECRUITMENT_NOT_ACTIVE);
        }
    }
}
