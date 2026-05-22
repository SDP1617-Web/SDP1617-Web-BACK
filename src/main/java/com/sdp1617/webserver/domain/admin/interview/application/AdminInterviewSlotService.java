package com.sdp1617.webserver.domain.admin.interview.application;

import com.sdp1617.webserver.domain.admin.interview.application.dto.response.InterviewSlotResponse;

import java.util.List;

public interface AdminInterviewSlotService {

    List<InterviewSlotResponse> getInterviewSlots(Long recruitmentId);
}
