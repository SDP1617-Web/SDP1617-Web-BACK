package com.sdp1617.webserver.domain.interview.infrastructure;

import com.sdp1617.webserver.domain.interview.entity.InterviewSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface InterviewSlotRepository extends JpaRepository<InterviewSlot, Long> {

    List<InterviewSlot> findAllByRecruitmentIdOrderBySlotDateTimeAsc(Long recruitmentId);

    List<InterviewSlot> findAllByRecruitmentIdAndSlotDateTimeAfterOrderBySlotDateTimeAsc(
            Long recruitmentId, LocalDateTime after);

    long countByRecruitmentIdAndIdIn(Long recruitmentId, Collection<Long> ids);
}
