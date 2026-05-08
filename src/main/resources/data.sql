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
