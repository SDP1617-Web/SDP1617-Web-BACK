package com.sdp1617.webserver.domain.portfolio.entity;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolio")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false, unique = true)
    private Apply application;

    @Column(nullable = false, length = 500)
    private String fileUrl;

    @Column(nullable = false, length = 200)
    private String fileName;

    @Builder
    public Portfolio(Apply application, String fileUrl, String fileName) {
        this.application = application;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }
}
