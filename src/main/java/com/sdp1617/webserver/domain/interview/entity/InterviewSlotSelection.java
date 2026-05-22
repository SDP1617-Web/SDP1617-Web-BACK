package com.sdp1617.webserver.domain.interview.entity;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "interview_slot_selection",
        uniqueConstraints = @UniqueConstraint(columnNames = {"interview_slot_id", "apply_id"}),
        indexes = @Index(name = "idx_interview_slot_selection_apply_id", columnList = "apply_id")
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewSlotSelection extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_slot_id", nullable = false)
    private InterviewSlot interviewSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id", nullable = false)
    private Apply apply;

    @Builder
    public InterviewSlotSelection(InterviewSlot interviewSlot, Apply apply) {
        this.interviewSlot = interviewSlot;
        this.apply = apply;
    }
}
