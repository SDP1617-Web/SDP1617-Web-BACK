package com.sdp1617.webserver.domain.admin.interview.application;

import com.sdp1617.webserver.domain.admin.interview.application.dto.response.InterviewSlotResponse;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlot;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlotSelection;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotRepository;
import com.sdp1617.webserver.domain.interview.infrastructure.InterviewSlotSelectionRepository;
import com.sdp1617.webserver.domain.recruitment.infrastructure.RecruitmentRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import com.sdp1617.webserver.domain.recruitment.application.exception.RecruitmentErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminInterviewSlotServiceImpl implements AdminInterviewSlotService {

    private final RecruitmentRepository recruitmentRepository;
    private final InterviewSlotRepository interviewSlotRepository;
    private final InterviewSlotSelectionRepository interviewSlotSelectionRepository;

    @Override
    public List<InterviewSlotResponse> getInterviewSlots(Long recruitmentId) {
        recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new ApplicationException(RecruitmentErrorCode.RECRUITMENT_NOT_FOUND));

        List<InterviewSlot> slots = interviewSlotRepository.findAllByRecruitmentIdOrderBySlotDateTimeAsc(recruitmentId);

        Map<Long, List<InterviewSlotSelection>> selectionsBySlotId = interviewSlotSelectionRepository
                .findAllByRecruitmentId(recruitmentId)
                .stream()
                .collect(Collectors.groupingBy(s -> s.getInterviewSlot().getId()));

        return slots.stream()
                .map(slot -> InterviewSlotResponse.of(
                        slot,
                        selectionsBySlotId.getOrDefault(slot.getId(), List.of())
                ))
                .toList();
    }
}
