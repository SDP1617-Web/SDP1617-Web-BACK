package com.sdp1617.webserver.domain.apply.entity;

import com.sdp1617.webserver.domain.question.entity.Question;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "application_answer",
        uniqueConstraints = @UniqueConstraint(columnNames = {"application_id", "question_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyAnswer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Apply application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public ApplyAnswer(Apply application, Question question, String content) {
        this.application = application;
        this.question = question;
        this.content = content;
    }
}
