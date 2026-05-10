package com.sdp1617.webserver.domain.review.entity;

import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false, length = 50)
    private String team;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Review(String name, int generation, String team, String content) {
        this.name = name;
        this.generation = generation;
        this.team = team;
        this.content = content;
    }
}
