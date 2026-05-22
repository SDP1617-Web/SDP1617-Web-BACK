package com.sdp1617.webserver.domain.recruitment.application.dto.response;

import com.sdp1617.webserver.domain.interview.entity.InterviewSlot;

import java.time.LocalDateTime;

public record InterviewSlotSimpleResponse(
        Long id,
        LocalDateTime slotDateTime
) {
    public static InterviewSlotSimpleResponse from(InterviewSlot slot) {
        return new InterviewSlotSimpleResponse(slot.getId(), slot.getSlotDateTime());
    }
}
