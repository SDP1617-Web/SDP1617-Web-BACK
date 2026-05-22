package com.sdp1617.webserver.domain.admin.interview.application.dto.response;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.apply.entity.TechRole;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlot;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlotSelection;

import java.time.LocalDateTime;
import java.util.List;

public record InterviewSlotResponse(
        Long slotId,
        LocalDateTime slotDateTime,
        List<ApplicantInfo> applicants
) {
    public static InterviewSlotResponse of(InterviewSlot slot, List<InterviewSlotSelection> selections) {
        return new InterviewSlotResponse(
                slot.getId(),
                slot.getSlotDateTime(),
                selections.stream()
                        .map(s -> ApplicantInfo.from(s.getApply()))
                        .toList()
        );
    }

    public record ApplicantInfo(
            Long applyId,
            String applicantName,
            String email,
            Department department,
            TechRole techRole
    ) {
        static ApplicantInfo from(Apply apply) {
            return new ApplicantInfo(
                    apply.getId(),
                    apply.getApplicant().getName(),
                    apply.getApplicant().getEmail(),
                    apply.getDepartment(),
                    apply.getTechRole()
            );
        }
    }
}
