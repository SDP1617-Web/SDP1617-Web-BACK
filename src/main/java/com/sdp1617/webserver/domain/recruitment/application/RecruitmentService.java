package com.sdp1617.webserver.domain.recruitment.application;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.apply.entity.TechRole;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotRepository;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.ApplicationFormDataResponse;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.InterviewSlotSimpleResponse;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.QuestionResponse;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.sdp1617.webserver.domain.recruitment.application.exception.RecruitmentErrorCode;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import com.sdp1617.webserver.domain.recruitment.infrastructure.RecruitmentRepository;
import com.sdp1617.webserver.domain.question.infrastructure.QuestionRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final QuestionRepository questionRepository;
    private final InterviewSlotRepository interviewSlotRepository;

    public RecruitmentResponse getActive() {
        Recruitment recruitment = recruitmentRepository.findActiveRecruitment(LocalDateTime.now())
                .orElseThrow(() -> new ApplicationException(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND));
        return RecruitmentResponse.from(recruitment);
    }

    public ApplicationFormDataResponse getQuestions(Long recruitmentId, Department department, TechRole techRole) {
        if (!recruitmentRepository.existsById(recruitmentId)) {
            throw new ApplicationException(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND);
        }
        TechRole effectiveTechRole = department == Department.TECH ? techRole : null;

        if (department == Department.TECH && effectiveTechRole == null) {
            throw new ApplicationException(RecruitmentErrorCode.TECH_ROLE_REQUIRED);
        }

        List<QuestionResponse> questions = questionRepository
                .findRequiredQuestions(recruitmentId, department, effectiveTechRole).stream()
                .map(QuestionResponse::from)
                .toList();

        List<InterviewSlotSimpleResponse> interviewSlots = interviewSlotRepository
                .findAllByRecruitmentIdAndSlotDateTimeAfterOrderBySlotDateTimeAsc(recruitmentId, LocalDateTime.now()).stream()
                .map(InterviewSlotSimpleResponse::from)
                .toList();

        return new ApplicationFormDataResponse(questions, interviewSlots);
    }
}
