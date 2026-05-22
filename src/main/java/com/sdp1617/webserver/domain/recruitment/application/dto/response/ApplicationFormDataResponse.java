package com.sdp1617.webserver.domain.recruitment.application.dto.response;

import java.util.List;

public record ApplicationFormDataResponse(
        List<QuestionResponse> questions,
        List<InterviewSlotSimpleResponse> interviewSlots
) {}
