package com.sdp1617.webserver.domain.recruitment.infrastructure;

import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findFirstByIsActiveTrue();
}
