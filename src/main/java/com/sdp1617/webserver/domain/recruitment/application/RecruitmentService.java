package com.sdp1617.webserver.domain.recruitment.application;

import com.sdp1617.webserver.domain.apply.entity.Department;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final QuestionRepository questionRepository;

    public RecruitmentResponse getActive() {
        Recruitment recruitment = recruitmentRepository.findFirstByIsActiveTrueOrderByCreatedAtDesc()
                .orElseThrow(() -> new ApplicationException(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND));
        return RecruitmentResponse.from(recruitment);
    }

    public List<QuestionResponse> getQuestions(Long recruitmentId, Department department) {
        if (!recruitmentRepository.existsById(recruitmentId)) {
            throw new ApplicationException(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND);
        }
        return questionRepository.findRequiredQuestions(recruitmentId, department).stream()
                .map(QuestionResponse::from)
                .toList();
    }
}
