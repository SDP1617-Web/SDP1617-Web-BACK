package com.sdp1617.webserver.domain.interview.infrastructure;

import com.sdp1617.webserver.domain.interview.entity.InterviewSlotSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewSlotSelectionRepository extends JpaRepository<InterviewSlotSelection, Long> {

    @Query("SELECT s FROM InterviewSlotSelection s " +
            "JOIN FETCH s.interviewSlot " +
            "JOIN FETCH s.apply a " +
            "JOIN FETCH a.applicant " +
            "WHERE s.interviewSlot.recruitment.id = :recruitmentId " +
            "AND a.status = com.sdp1617.webserver.domain.apply.entity.ApplyStatus.INTERVIEW")
    List<InterviewSlotSelection> findAllByRecruitmentId(@Param("recruitmentId") Long recruitmentId);

    @Query("SELECT s FROM InterviewSlotSelection s JOIN FETCH s.interviewSlot WHERE s.apply.id = :applyId")
    List<InterviewSlotSelection> findAllByApplyId(@Param("applyId") Long applyId);
}
