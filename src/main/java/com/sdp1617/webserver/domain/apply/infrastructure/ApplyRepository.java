package com.sdp1617.webserver.domain.apply.infrastructure;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByRecruitmentIdAndApplicantId(Long recruitmentId, Long applicantId);
}
