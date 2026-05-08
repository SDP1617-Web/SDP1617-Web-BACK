package com.sdp1617.webserver.domain.portfolio.infrastructure;

import com.sdp1617.webserver.domain.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    boolean existsByApplicationId(Long applicationId);
}
