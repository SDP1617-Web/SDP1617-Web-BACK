package com.sdp1617.webserver.domain.question.entity;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @Enumerated(EnumType.STRING)
    @Column
    private Department department;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int maxLength;

    @Column(nullable = false)
    private int sequence;

    @Builder
    public Question(Recruitment recruitment, Department department, String content, int maxLength, int sequence) {
        this.recruitment = recruitment;
        this.department = department;
        this.content = content;
        this.maxLength = maxLength;
        this.sequence = sequence;
    }
}
