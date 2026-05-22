package com.sdp1617.webserver.domain.admin.applicationform.application.dto.response;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.ApplyAnswer;
import com.sdp1617.webserver.domain.apply.entity.ApplyStatus;
import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlotSelection;
import com.sdp1617.webserver.domain.question.entity.Question;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ApplicationFormDetailResponse(
        Long id,
        String recruitmentTitle,
        String recruitmentSemester,
        String applicantName,
        String phone,
        LocalDate birthDate,
        String email,
        String university,
        String major,
        Department department,
        ApplyStatus status,
        LocalDateTime submittedAt,
        String reviewerNote,
        List<AnswerResponse> answers,
        List<InterviewSlotInfo> interviewSlots
) {
    public static ApplicationFormDetailResponse from(Apply apply, List<ApplyAnswer> applyAnswers, List<InterviewSlotSelection> slotSelections) {
        return new ApplicationFormDetailResponse(
                apply.getId(),
                apply.getRecruitment().getTitle(),
                apply.getRecruitment().getSemester(),
                apply.getApplicant().getName(),
                apply.getApplicant().getPhone(),
                apply.getApplicant().getBirthDate(),
                apply.getApplicant().getEmail(),
                apply.getApplicant().getUniversity(),
                apply.getApplicant().getMajor(),
                apply.getDepartment(),
                apply.getStatus(),
                apply.getSubmittedAt(),
                apply.getReviewerNote(),
                applyAnswers.stream()
                        .map(AnswerResponse::from)
                        .toList(),
                slotSelections.stream()
                        .map(s -> new InterviewSlotInfo(s.getInterviewSlot().getId(), s.getInterviewSlot().getSlotDateTime()))
                        .toList()
        );
    }

    public record AnswerResponse(
            Long questionId,
            int sequence,
            String questionContent,
            String answerContent
    ) {
        static AnswerResponse from(ApplyAnswer applyAnswer) {
            Question question = applyAnswer.getQuestion();
            return new AnswerResponse(
                    question.getId(),
                    question.getSequence(),
                    question.getContent(),
                    applyAnswer.getContent()
            );
        }
    }

    public record InterviewSlotInfo(
            Long slotId,
            LocalDateTime slotDateTime
    ) {}
}
