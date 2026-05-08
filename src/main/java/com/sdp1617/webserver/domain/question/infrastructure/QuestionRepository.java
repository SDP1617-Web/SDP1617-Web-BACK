package com.sdp1617.webserver.domain.question.infrastructure;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.id IN :ids AND q.recruitment.id = :recruitmentId AND (q.department IS NULL OR q.department = :department)")
    List<Question> findValidQuestions(@Param("ids") List<Long> ids, @Param("recruitmentId") Long recruitmentId, @Param("department") Department department);

    @Query("SELECT q FROM Question q WHERE q.recruitment.id = :recruitmentId AND (q.department IS NULL OR q.department = :department)")
    List<Question> findRequiredQuestions(@Param("recruitmentId") Long recruitmentId, @Param("department") Department department);
}
