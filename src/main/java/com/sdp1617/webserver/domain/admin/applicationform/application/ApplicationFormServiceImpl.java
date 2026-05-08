package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormPreviewResponse;
import org.springframework.stereotype.Service;

@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {

    @Override
    public ApplicationFormPreviewResponse getApplicationForm(Long applicationFormId) {
        if (applicationFormId == null || applicationFormId <= 0) {
            throw new IllegalArgumentException("applicationFormId must be positive: " + applicationFormId);
        }

        return new ApplicationFormPreviewResponse(
                applicationFormId,
                "지원서 조회용 임시 응답입니다. 실제 엔티티 연결 전까지는 이 형태를 사용합니다."
        );
    }
}
