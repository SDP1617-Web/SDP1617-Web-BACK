package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormDetailResponse;

public interface ApplicationFormService {

    ApplicationFormDetailResponse getApplicationForm(Long applicationId);
}
