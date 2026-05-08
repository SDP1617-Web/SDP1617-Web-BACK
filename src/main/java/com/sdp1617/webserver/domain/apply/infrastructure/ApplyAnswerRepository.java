package com.sdp1617.webserver.domain.apply.infrastructure;

import com.sdp1617.webserver.domain.apply.entity.ApplyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyAnswerRepository extends JpaRepository<ApplyAnswer, Long> {
}
