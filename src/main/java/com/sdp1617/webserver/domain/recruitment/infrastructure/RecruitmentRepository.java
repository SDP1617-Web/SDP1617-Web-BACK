package com.sdp1617.webserver.domain.recruitment.infrastructure;

import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    @Query("SELECT r FROM Recruitment r WHERE r.isActive = true AND r.startAt <= :now AND r.deadlineAt >= :now ORDER BY r.createdAt DESC")
    Optional<Recruitment> findActiveRecruitment(@Param("now") LocalDateTime now);
}
