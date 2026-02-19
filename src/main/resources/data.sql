INSERT INTO argus_group (id, active)
VALUES ('11111111-1111-1111-1111-111111111111', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO argus_group (id, active)
VALUES ('22222222-2222-2222-2222-222222222222', false)
ON CONFLICT (id) DO NOTHING;

INSERT INTO argus_worker (id, group_id)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111')
ON CONFLICT (id) DO NOTHING;

INSERT INTO argus_worker (id, group_id)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '11111111-1111-1111-1111-111111111111')
ON CONFLICT (id) DO NOTHING;

INSERT INTO argus_worker (id, group_id)
VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', '22222222-2222-2222-2222-222222222222')
ON CONFLICT (id) DO NOTHING;