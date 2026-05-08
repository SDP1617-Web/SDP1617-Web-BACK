package com.sdp1617.webserver.domain.admin.applicationform.application;

import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationFormServiceImpl implements ApplicationFormService {

    @Override
    public ApplicationFormPreviewResponse getApplicationForm(Long applicationFormId) {
        if (!Long.valueOf(1L).equals(applicationFormId)) {
            throw new com.sdp1617.webserver.global.common.exception.ApplicationException(
                    com.sdp1617.webserver.global.common.exception.ErrorCode.NOT_FOUND,
                    "Application form not found: " + applicationFormId
            );
        }

        return new ApplicationFormPreviewResponse(
                applicationFormId,
                "지원서 조회용 임시 응답입니다. 실제 엔티티 연결 전까지는 이 형태를 사용합니다."
        );
    }
}
