package com.sdp1617.webserver.domain.applicant.entity;

import com.sdp1617.webserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "applicant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String university;

    @Column(length = 100)
    private String major;

    @Builder
    public Applicant(String name, String phone, LocalDate birthDate, String email, String university, String major) {
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.university = university;
        this.major = major;
    }
}
