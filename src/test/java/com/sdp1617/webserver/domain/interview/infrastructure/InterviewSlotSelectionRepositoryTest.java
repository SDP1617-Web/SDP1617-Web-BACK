package com.sdp1617.webserver.domain.interview.infrastructure;

import com.sdp1617.webserver.domain.applicant.entity.Applicant;
import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.entity.ApplyStatus;
import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlot;
import com.sdp1617.webserver.domain.interview.entity.InterviewSlotSelection;
import com.sdp1617.webserver.domain.recruitment.entity.Recruitment;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class InterviewSlotSelectionRepositoryTest {

    @Autowired
    private InterviewSlotSelectionRepository interviewSlotSelectionRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void findAllByRecruitmentId_INTERVIEW_상태인_지원자만_반환() {
        Recruitment recruitment = em.persist(Recruitment.builder()
                .title("2026 테스트 모집").semester("2026-1")
                .startAt(LocalDateTime.now().minusDays(1))
                .deadlineAt(LocalDateTime.now().plusDays(30))
                .build());

        Applicant applicant1 = em.persist(Applicant.builder()
                .name("홍길동").phone("010-1111-1111").birthDate(LocalDate.of(2000, 1, 1))
                .email("pending@test.com").build());
        Applicant applicant2 = em.persist(Applicant.builder()
                .name("김철수").phone("010-2222-2222").birthDate(LocalDate.of(2000, 2, 2))
                .email("interview@test.com").build());

        Apply pendingApply = em.persist(Apply.builder()
                .recruitment(recruitment).applicant(applicant1).department(Department.TECH).build());
        Apply interviewApply = em.persist(Apply.builder()
                .recruitment(recruitment).applicant(applicant2).department(Department.DESIGN).build());

        EntityManager nativeEm = em.getEntityManager();
        nativeEm.createNativeQuery("UPDATE application SET status = 'INTERVIEW' WHERE id = :id")
                .setParameter("id", interviewApply.getId())
                .executeUpdate();

        InterviewSlot slot = em.persist(InterviewSlot.builder()
                .recruitment(recruitment)
                .slotDateTime(LocalDateTime.of(2026, 6, 14, 10, 0))
                .build());

        em.persist(InterviewSlotSelection.builder().interviewSlot(slot).apply(pendingApply).build());
        em.persist(InterviewSlotSelection.builder().interviewSlot(slot).apply(interviewApply).build());
        em.flush();
        em.clear();

        List<InterviewSlotSelection> result = interviewSlotSelectionRepository.findAllByRecruitmentId(recruitment.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getApply().getStatus()).isEqualTo(ApplyStatus.INTERVIEW);
        assertThat(result.get(0).getApply().getApplicant().getEmail()).isEqualTo("interview@test.com");
    }

    @Test
    void findAllByRecruitmentId_INTERVIEW_없으면_빈_리스트_반환() {
        Recruitment recruitment = em.persist(Recruitment.builder()
                .title("2026 테스트 모집").semester("2026-2")
                .startAt(LocalDateTime.now().minusDays(1))
                .deadlineAt(LocalDateTime.now().plusDays(30))
                .build());

        Applicant applicant = em.persist(Applicant.builder()
                .name("이영희").phone("010-3333-3333").birthDate(LocalDate.of(2000, 3, 3))
                .email("nointerview@test.com").build());

        Apply pendingApply = em.persist(Apply.builder()
                .recruitment(recruitment).applicant(applicant).department(Department.RESEARCH).build());

        InterviewSlot slot = em.persist(InterviewSlot.builder()
                .recruitment(recruitment)
                .slotDateTime(LocalDateTime.of(2026, 6, 15, 10, 0))
                .build());

        em.persist(InterviewSlotSelection.builder().interviewSlot(slot).apply(pendingApply).build());
        em.flush();
        em.clear();

        List<InterviewSlotSelection> result = interviewSlotSelectionRepository.findAllByRecruitmentId(recruitment.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByApplyId_해당_지원서의_슬롯만_반환() {
        Recruitment recruitment = em.persist(Recruitment.builder()
                .title("2026 테스트 모집").semester("2026-3")
                .startAt(LocalDateTime.now().minusDays(1))
                .deadlineAt(LocalDateTime.now().plusDays(30))
                .build());

        Applicant applicant = em.persist(Applicant.builder()
                .name("박영수").phone("010-4444-4444").birthDate(LocalDate.of(2000, 4, 4))
                .email("slot@test.com").build());

        Apply apply = em.persist(Apply.builder()
                .recruitment(recruitment).applicant(applicant).department(Department.TECH).build());

        InterviewSlot slot1 = em.persist(InterviewSlot.builder()
                .recruitment(recruitment).slotDateTime(LocalDateTime.of(2026, 6, 14, 10, 0)).build());
        InterviewSlot slot2 = em.persist(InterviewSlot.builder()
                .recruitment(recruitment).slotDateTime(LocalDateTime.of(2026, 6, 14, 14, 0)).build());

        em.persist(InterviewSlotSelection.builder().interviewSlot(slot1).apply(apply).build());
        em.persist(InterviewSlotSelection.builder().interviewSlot(slot2).apply(apply).build());
        em.flush();
        em.clear();

        List<InterviewSlotSelection> result = interviewSlotSelectionRepository.findAllByApplyId(apply.getId());

        assertThat(result).hasSize(2);
        assertThat(result).extracting(s -> s.getInterviewSlot().getSlotDateTime())
                .containsExactlyInAnyOrder(
                        LocalDateTime.of(2026, 6, 14, 10, 0),
                        LocalDateTime.of(2026, 6, 14, 14, 0)
                );
    }
}
