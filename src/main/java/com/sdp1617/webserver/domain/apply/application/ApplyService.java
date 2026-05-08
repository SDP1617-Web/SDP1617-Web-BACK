package com.sdp1617.webserver.domain.apply.application;

import com.sdp1617.webserver.domain.applicant.entity.Applicant;
import com.sdp1617.webserver.domain.applicant.infrastructure.ApplicantRepository;
import com.sdp1617.webserver.domain.apply.application.dto.request.ApplySubmitRequest;
import com.sdp1617.webserver.domain.apply.application.dto.response.ApplySubmitResponse;
import com.sdp1617.webserver.domain.apply.application.exception.ApplyErrorCode;
import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.ApplyAnswer;
import com.sdp1617.webserver.domain.apply.infrastructure.ApplyAnswerRepository;
import com.sdp1617.webserver.domain.apply.infrastructure.ApplyRepository;
import com.sdp1617.webserver.domain.question.entity.Question;
import com.sdp1617.webserver.domain.question.infrastructure.QuestionRepository;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import com.sdp1617.webserver.domain.recruitment.infrastructure.RecruitmentRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyService {

    private final RecruitmentRepository recruitmentRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplyRepository applicationRepository;
    private final ApplyAnswerRepository applicationAnswerRepository;
    private final QuestionRepository questionRepository;

    public ApplySubmitResponse submit(Long recruitmentId, ApplySubmitRequest request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new ApplicationException(ApplyErrorCode.RECRUITMENT_NOT_FOUND));

        recruitment.validateActive();

        Applicant applicant = applicantRepository.findByEmail(request.email())
                .orElseGet(() -> applicantRepository.save(Applicant.builder()
                        .name(request.name())
                        .phone(request.phone())
                        .birthDate(request.birthDate())
                        .email(request.email())
                        .university(request.university())
                        .major(request.major())
                        .build()));

        if (applicationRepository.existsByRecruitmentIdAndApplicantId(recruitmentId, applicant.getId())) {
            throw new ApplicationException(ApplyErrorCode.ALREADY_APPLIED);
        }

        List<Long> questionIds = request.answers().stream()
                .map(ApplySubmitRequest.AnswerRequest::questionId)
                .toList();

        List<Question> validQuestions = questionRepository.findValidQuestions(questionIds, recruitmentId, request.department());

        if (validQuestions.size() != questionIds.size()) {
            throw new ApplicationException(ApplyErrorCode.INVALID_QUESTION);
        }

        List<Question> requiredQuestions = questionRepository.findRequiredQuestions(recruitmentId, request.department());
        Set<Long> answeredIds = questionIds.stream().collect(Collectors.toSet());
        boolean hasMissing = requiredQuestions.stream().anyMatch(q -> !answeredIds.contains(q.getId()));
        if (hasMissing) {
            throw new ApplicationException(ApplyErrorCode.MISSING_REQUIRED_QUESTION);
        }

        Apply application = applicationRepository.save(Apply.builder()
                .recruitment(recruitment)
                .applicant(applicant)
                .department(request.department())
                .build());

        Map<Long, Question> questionMap = validQuestions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        List<ApplyAnswer> answers = request.answers().stream()
                .map(answerRequest -> ApplyAnswer.builder()
                        .application(application)
                        .question(questionMap.get(answerRequest.questionId()))
                        .content(answerRequest.content())
                        .build())
                .toList();

        applicationAnswerRepository.saveAll(answers);

        return new ApplySubmitResponse(application.getId());
    }
}
