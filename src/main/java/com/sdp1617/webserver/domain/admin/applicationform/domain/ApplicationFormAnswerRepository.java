package com.sdp1617.webserver.domain.admin.applicationform.domain;

import com.sdp1617.webserver.domain.apply.entity.ApplyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationFormAnswerRepository extends JpaRepository<ApplyAnswer, Long> {

    @Query("SELECT aa FROM ApplyAnswer aa " +
            "JOIN FETCH aa.question q " +
            "WHERE aa.application.id = :applicationId " +
            "ORDER BY q.sequence ASC")
    List<ApplyAnswer> findAllByApplicationIdOrderByQuestionSequenceAsc(@Param("applicationId") Long applicationId);
}
