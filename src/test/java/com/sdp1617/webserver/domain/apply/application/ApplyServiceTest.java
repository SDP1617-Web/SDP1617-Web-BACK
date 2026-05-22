package com.sdp1617.webserver.domain.apply.application;

import com.sdp1617.webserver.domain.applicant.entity.Applicant;
import com.sdp1617.webserver.domain.applicant.infrastructure.ApplicantRepository;
import com.sdp1617.webserver.domain.apply.application.dto.request.ApplySubmitRequest;
import com.sdp1617.webserver.domain.apply.application.exception.ApplyErrorCode;
import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.apply.entity.TechRole;
import com.sdp1617.webserver.domain.apply.infrastructure.ApplyAnswerRepository;
import com.sdp1617.webserver.domain.apply.infrastructure.ApplyRepository;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotRepository;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotSelectionRepository;
import com.sdp1617.webserver.domain.question.entity.Question;
import com.sdp1617.webserver.domain.question.infrastructure.QuestionRepository;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import com.sdp1617.webserver.domain.recruitment.infrastructure.RecruitmentRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApplyServiceTest {

    @InjectMocks
    private ApplyService applyService;

    @Mock private RecruitmentRepository recruitmentRepository;
    @Mock private ApplicantRepository applicantRepository;
    @Mock private ApplyRepository applyRepository;
    @Mock private ApplyAnswerRepository applyAnswerRepository;
    @Mock private QuestionRepository questionRepository;
    @Mock private InterviewSlotRepository interviewSlotRepository;
    @Mock private InterviewSlotSelectionRepository interviewSlotSelectionRepository;

    private static final Long RECRUITMENT_ID = 1L;

    private Applicant applicant;
    private Apply savedApply;
    private Question question;

    @BeforeEach
    void setUp() {
        Recruitment activeRecruitment = Recruitment.builder()
                .title("2026 테스트 모집").semester("2026-1")
                .startAt(LocalDateTime.now().minusDays(1))
                .deadlineAt(LocalDateTime.now().plusDays(30))
                .build();

        applicant = Applicant.builder()
                .name("홍길동").phone("010-1234-5678")
                .birthDate(LocalDate.of(2000, 1, 1))
                .email("test@test.com").build();

        savedApply = mock(Apply.class);
        given(savedApply.getId()).willReturn(10L);

        question = mock(Question.class);
        given(question.getId()).willReturn(1L);

        given(recruitmentRepository.findById(RECRUITMENT_ID)).willReturn(Optional.of(activeRecruitment));
        given(applicantRepository.findByEmail("test@test.com")).willReturn(Optional.of(applicant));
        given(applyRepository.existsByRecruitmentIdAndApplicantId(eq(RECRUITMENT_ID), any())).willReturn(false);
        given(questionRepository.findValidQuestions(anyList(), eq(RECRUITMENT_ID), any(), any())).willReturn(List.of(question));
        given(questionRepository.findRequiredQuestions(eq(RECRUITMENT_ID), any(), any())).willReturn(List.of(question));
        given(applyRepository.save(any())).willReturn(savedApply);
        given(applyAnswerRepository.saveAll(any())).willReturn(List.of());
    }

    private ApplySubmitRequest requestWith(List<Long> slotIds) {
        return new ApplySubmitRequest(
                "홍길동", "010-1234-5678", LocalDate.of(2000, 1, 1),
                "test@test.com", "서강대", "컴퓨터공학",
                Department.TECH, TechRole.BACKEND,
                List.of(new ApplySubmitRequest.AnswerRequest(1L, "답변입니다.")),
                slotIds
        );
    }

    @Test
    void 중복_슬롯_ID_포함시_예외() {
        Throwable thrown = catchThrowable(() ->
                applyService.submit(RECRUITMENT_ID, requestWith(List.of(1L, 1L))));

        assertThat(thrown).isInstanceOf(ApplicationException.class);
        assertThat(((ApplicationException) thrown).getErrorCode())
                .isEqualTo(ApplyErrorCode.DUPLICATE_INTERVIEW_SLOT);
    }

    @Test
    void 다른_모집_슬롯_ID_포함시_예외() {
        given(interviewSlotRepository.countByRecruitmentIdAndIdIn(eq(RECRUITMENT_ID), anyCollection()))
                .willReturn(1L); // 요청은 2개인데 유효한 건 1개

        Throwable thrown = catchThrowable(() ->
                applyService.submit(RECRUITMENT_ID, requestWith(List.of(1L, 99L))));

        assertThat(thrown).isInstanceOf(ApplicationException.class);
        assertThat(((ApplicationException) thrown).getErrorCode())
                .isEqualTo(ApplyErrorCode.INVALID_INTERVIEW_SLOT);
    }

    @Test
    void 존재하지_않는_슬롯_ID_포함시_예외() {
        given(interviewSlotRepository.countByRecruitmentIdAndIdIn(eq(RECRUITMENT_ID), anyCollection()))
                .willReturn(0L);

        Throwable thrown = catchThrowable(() ->
                applyService.submit(RECRUITMENT_ID, requestWith(List.of(999L))));

        assertThat(thrown).isInstanceOf(ApplicationException.class);
        assertThat(((ApplicationException) thrown).getErrorCode())
                .isEqualTo(ApplyErrorCode.INVALID_INTERVIEW_SLOT);
    }

    @Test
    void 정상_슬롯_선택_제출_성공() {
        given(interviewSlotRepository.countByRecruitmentIdAndIdIn(eq(RECRUITMENT_ID), anyCollection()))
                .willReturn(2L);
        given(interviewSlotSelectionRepository.saveAll(any())).willReturn(List.of());

        applyService.submit(RECRUITMENT_ID, requestWith(List.of(1L, 2L)));

        verify(interviewSlotSelectionRepository).saveAll(argThat(list ->
                ((List<?>) list).size() == 2
        ));
    }

    @Test
    void 빈_슬롯_리스트_제출시_슬롯_저장_호출_안됨() {
        applyService.submit(RECRUITMENT_ID, requestWith(List.of()));

        verify(interviewSlotSelectionRepository, never()).saveAll(any());
    }
}
