package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormDetailResponse;
import com.sdp1617.webserver.domain.admin.applicationform.domain.ApplicationFormAnswerRepository;
import com.sdp1617.webserver.domain.admin.applicationform.domain.ApplicationFormRepository;
import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.ApplyAnswer;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import com.sdp1617.webserver.global.common.exception.ErrorCode;
import com.sdp1617.webserver.global.common.exception.InvalidArgumentException;
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

    @Override
    public ApplicationFormDetailResponse getApplicationForm(Long applicationId) {
        if (applicationId == null || applicationId <= 0) {
            throw new InvalidArgumentException("applicationId must be positive: " + applicationId);
        }

        Apply apply = applicationFormRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, "Application not found: " + applicationId));

        List<ApplyAnswer> applyAnswers = applicationFormAnswerRepository.findAllByApplicationIdOrderByQuestionSequenceAsc(applicationId);
        return ApplicationFormDetailResponse.from(apply, applyAnswers);
    }
}
