package com.sdp1617.webserver.domain.applicant.infrastructure;

import com.sdp1617.webserver.domain.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByEmail(String email);
}
