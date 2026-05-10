package com.sdp1617.webserver.domain.admin.applicationform.domain;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormRepository extends JpaRepository<Apply, Long> {
}
