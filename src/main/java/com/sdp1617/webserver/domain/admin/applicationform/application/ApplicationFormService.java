package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormPreviewResponse;

public interface ApplicationFormService {

    ApplicationFormPreviewResponse getApplicationForm(Long applicationFormId);
}
