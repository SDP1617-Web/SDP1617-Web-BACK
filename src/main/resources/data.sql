INSERT INTO review (name, generation, team, content, is_display, created_at, updated_at)
SELECT '홍길동', 16, '테크 - 백엔드', '실제 서비스를 개발하며 스프링 부트를 깊게 익힐 수 있었습니다.', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM review WHERE name = '홍길동' AND generation = 16);

INSERT INTO review (name, generation, team, content, is_display, created_at, updated_at)
SELECT '김철수', 16, '테크 - 프론트엔드', '리액트로 실제 서비스를 만들어보는 경험이 정말 값졌습니다.', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM review WHERE name = '김철수' AND generation = 16);

INSERT INTO review (name, generation, team, content, is_display, created_at, updated_at)
SELECT '이영희', 17, '디자인', '개발자와 직접 소통하며 디자인 시스템을 구축하는 과정이 좋았습니다.', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM review WHERE name = '이영희' AND generation = 17);

INSERT INTO review (name, generation, team, content, is_display, created_at, updated_at)
SELECT '박영수', 17, '리서치', '논문 리뷰부터 실험까지 체계적으로 연구하는 방법을 배울 수 있었습니다.', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM review WHERE name = '박영수' AND generation = 17);

INSERT INTO recruitment (title, semester, start_at, deadline_at, is_active, created_at, updated_at)
SELECT '2026 1학기 모집', '2026-1', '2026-01-01 00:00:00', '2099-12-31 23:59:59', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM recruitment WHERE semester = '2026-1');

INSERT INTO question (recruitment_id, department, content, max_length, sequence, created_at, updated_at)
SELECT 1, NULL, '자기소개를 해주세요.', 500, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 1 AND department IS NULL);

INSERT INTO question (recruitment_id, department, content, max_length, sequence, created_at, updated_at)
SELECT 1, NULL, '지원 동기를 작성해주세요.', 1000, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 2 AND department IS NULL);

INSERT INTO question (recruitment_id, department, content, max_length, sequence, created_at, updated_at)
SELECT 1, 'TECH', 'Github 링크 또는 기술 스택을 작성해주세요.', 500, 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 3 AND department = 'TECH');

INSERT INTO question (recruitment_id, department, content, max_length, sequence, created_at, updated_at)
SELECT 1, 'DESIGN', '포트폴리오 링크 또는 작업물을 소개해주세요.', 500, 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 3 AND department = 'DESIGN');

INSERT INTO question (recruitment_id, department, content, max_length, sequence, created_at, updated_at)
SELECT 1, 'RESEARCH', '관심 있는 연구 분야를 작성해주세요.', 500, 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 3 AND department = 'RESEARCH');

INSERT INTO question (recruitment_id, department, tech_role, content, max_length, sequence, created_at, updated_at)
SELECT 1, 'TECH', 'FRONTEND', '사용해본 프론트엔드 프레임워크와 경험을 작성해주세요.', 500, 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 4 AND tech_role = 'FRONTEND');

INSERT INTO question (recruitment_id, department, tech_role, content, max_length, sequence, created_at, updated_at)
SELECT 1, 'TECH', 'BACKEND', '사용해본 백엔드 기술 스택과 경험을 작성해주세요.', 500, 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM question WHERE recruitment_id = 1 AND sequence = 4 AND tech_role = 'BACKEND');

INSERT INTO interview_slot (recruitment_id, slot_date_time, created_at, updated_at)
SELECT 1, '2026-06-14 10:00:00', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM interview_slot WHERE recruitment_id = 1 AND slot_date_time = '2026-06-14 10:00:00');

INSERT INTO interview_slot (recruitment_id, slot_date_time, created_at, updated_at)
SELECT 1, '2026-06-14 11:00:00', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM interview_slot WHERE recruitment_id = 1 AND slot_date_time = '2026-06-14 11:00:00');

INSERT INTO interview_slot (recruitment_id, slot_date_time, created_at, updated_at)
SELECT 1, '2026-06-14 14:00:00', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM interview_slot WHERE recruitment_id = 1 AND slot_date_time = '2026-06-14 14:00:00');

INSERT INTO interview_slot (recruitment_id, slot_date_time, created_at, updated_at)
SELECT 1, '2026-06-15 10:00:00', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM interview_slot WHERE recruitment_id = 1 AND slot_date_time = '2026-06-15 10:00:00');

INSERT INTO interview_slot (recruitment_id, slot_date_time, created_at, updated_at)
SELECT 1, '2026-06-15 13:00:00', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM interview_slot WHERE recruitment_id = 1 AND slot_date_time = '2026-06-15 13:00:00');
