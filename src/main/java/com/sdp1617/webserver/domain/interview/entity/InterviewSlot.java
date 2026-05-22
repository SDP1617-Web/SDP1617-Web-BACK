package com.sdp1617.webserver.domain.interview.entity;

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
        name = "interview_slot",
        uniqueConstraints = @UniqueConstraint(columnNames = {"recruitment_id", "slot_date_time"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewSlot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @Column(nullable = false)
    private LocalDateTime slotDateTime;

    @Builder
    public InterviewSlot(Recruitment recruitment, LocalDateTime slotDateTime) {
        this.recruitment = recruitment;
        this.slotDateTime = slotDateTime;
    }
}
