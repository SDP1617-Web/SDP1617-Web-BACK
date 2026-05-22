package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormDetailResponse;
import com.sdp1617.webserver.domain.admin.applicationform.domain.ApplicationFormAnswerRepository;
import com.sdp1617.webserver.domain.admin.applicationform.domain.ApplicationFormRepository;
import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.ApplyAnswer;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlotSelection;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotSelectionRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import com.sdp1617.webserver.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationFormServiceImpl implements ApplicationFormService {

    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationFormAnswerRepository applicationFormAnswerRepository;
    private final InterviewSlotSelectionRepository interviewSlotSelectionRepository;

    @Override
    public ApplicationFormDetailResponse getApplicationForm(Long recruitmentId, Long applicationId) {
        Apply apply = applicationFormRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, "Application not found: " + applicationId));

        if (!apply.getRecruitment().getId().equals(recruitmentId)) {
            throw new ApplicationException(ErrorCode.NOT_FOUND, "Application not found: " + applicationId);
        }

        List<ApplyAnswer> applyAnswers = applicationFormAnswerRepository.findAllByApplicationIdOrderByQuestionSequenceAsc(applicationId);
        List<InterviewSlotSelection> slotSelections = interviewSlotSelectionRepository.findAllByApplyId(applicationId);
        return ApplicationFormDetailResponse.from(apply, applyAnswers, slotSelections);
    }
}
