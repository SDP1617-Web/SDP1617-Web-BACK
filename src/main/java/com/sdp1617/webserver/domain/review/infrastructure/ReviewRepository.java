package com.sdp1617.webserver.domain.review.infrastructure;

import com.sdp1617.webserver.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
