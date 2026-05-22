package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.domain.ApplicationFormAnswerRepository;
import com.sdp1617.webserver.domain.admin.applicationform.domain.ApplicationFormRepository;
import com.sdp1617.webserver.domain.applicant.entity.Applicant;
import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotSelectionRepository;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import com.sdp1617.webserver.global.common.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ApplicationFormServiceImplTest {

    @InjectMocks
    private ApplicationFormServiceImpl applicationFormService;

    @Mock private ApplicationFormRepository applicationFormRepository;
    @Mock private ApplicationFormAnswerRepository applicationFormAnswerRepository;
    @Mock private InterviewSlotSelectionRepository interviewSlotSelectionRepository;

    @Test
    void 다른_모집의_지원서_조회시_404() {
        Long recruitmentId = 1L;
        Long applicationId = 10L;

        Recruitment otherRecruitment = mock(Recruitment.class);
        given(otherRecruitment.getId()).willReturn(999L);

        Apply apply = mock(Apply.class);
        given(apply.getRecruitment()).willReturn(otherRecruitment);
        given(applicationFormRepository.findById(applicationId)).willReturn(Optional.of(apply));

        Throwable thrown = catchThrowable(() ->
                applicationFormService.getApplicationForm(recruitmentId, applicationId));

        assertThat(thrown).isInstanceOf(ApplicationException.class);
        assertThat(((ApplicationException) thrown).getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
    }

    @Test
    void 존재하지_않는_지원서_조회시_404() {
        given(applicationFormRepository.findById(999L)).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() ->
                applicationFormService.getApplicationForm(1L, 999L));

        assertThat(thrown).isInstanceOf(ApplicationException.class);
        assertThat(((ApplicationException) thrown).getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
    }

    @Test
    void 정상_조회시_슬롯_정보_포함() {
        Long recruitmentId = 1L;
        Long applicationId = 10L;

        Recruitment recruitment = mock(Recruitment.class);
        given(recruitment.getId()).willReturn(recruitmentId);

        Apply apply = mock(Apply.class);
        given(apply.getRecruitment()).willReturn(recruitment);
        given(apply.getId()).willReturn(applicationId);

        Applicant applicant = mock(Applicant.class);
        given(apply.getApplicant()).willReturn(applicant);

        given(applicationFormRepository.findById(applicationId)).willReturn(Optional.of(apply));
        given(applicationFormAnswerRepository.findAllByApplicationIdOrderByQuestionSequenceAsc(applicationId))
                .willReturn(List.of());
        given(interviewSlotSelectionRepository.findAllByApplyId(applicationId))
                .willReturn(List.of());

        var response = applicationFormService.getApplicationForm(recruitmentId, applicationId);

        assertThat(response.interviewSlots()).isNotNull().isEmpty();
    }
}
