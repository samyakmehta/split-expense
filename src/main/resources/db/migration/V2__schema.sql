-- add sample users
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('8fcd8244-d410-11eb-b8bc-0242ac130003'::uuid, 1);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('cf7e9716-d410-11eb-b8bc-0242ac130003'::uuid, 1);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('d6a8fec8-d410-11eb-b8bc-0242ac130003'::uuid, 1);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('dead0538-d410-11eb-b8bc-0242ac130003'::uuid, 1);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('e5d20156-d410-11eb-b8bc-0242ac130003'::uuid, 1);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('efead55a-d410-11eb-b8bc-0242ac130003'::uuid, 1);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('f60535f2-d410-11eb-b8bc-0242ac130003'::uuid, 1);

-- add sample groups
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('65ecee96-d411-11eb-b8bc-0242ac130003'::uuid, 2);
INSERT INTO SPLIT_EXP_USER_ENTITY VALUES ('70a32b0c-d411-11eb-b8bc-0242ac130003'::uuid, 2);

-- add user information
INSERT INTO SPLIT_EXP_USER VALUES ('8fcd8244-d410-11eb-b8bc-0242ac130003'::uuid, 'user1@example.com', 'User one');
INSERT INTO SPLIT_EXP_USER VALUES ('cf7e9716-d410-11eb-b8bc-0242ac130003'::uuid, 'user2@example.com', 'User two');
INSERT INTO SPLIT_EXP_USER VALUES ('d6a8fec8-d410-11eb-b8bc-0242ac130003'::uuid, 'user3@example.com', 'User three');
INSERT INTO SPLIT_EXP_USER VALUES ('dead0538-d410-11eb-b8bc-0242ac130003'::uuid, 'user4@example.com', 'User four');
INSERT INTO SPLIT_EXP_USER VALUES ('e5d20156-d410-11eb-b8bc-0242ac130003'::uuid, 'user5@example.com', 'User five');
INSERT INTO SPLIT_EXP_USER VALUES ('efead55a-d410-11eb-b8bc-0242ac130003'::uuid, 'user6@example.com', 'User six');
INSERT INTO SPLIT_EXP_USER VALUES ('f60535f2-d410-11eb-b8bc-0242ac130003'::uuid, 'user7@example.com', 'User seven');

-- add group information
INSERT INTO SPLIT_EXP_GROUP VALUES ('65ecee96-d411-11eb-b8bc-0242ac130003'::uuid, 'Group one');
INSERT INTO SPLIT_EXP_GROUP VALUES ('70a32b0c-d411-11eb-b8bc-0242ac130003'::uuid, 'Group Two');

-- add users to groups
-- Group one -> User one, User two, User three
-- Group two -> User three, User four, User five
INSERT INTO SPLIT_EXP_USER_GROUP_MAPPING VALUES (gen_random_uuid(), '65ecee96-d411-11eb-b8bc-0242ac130003'::uuid, '8fcd8244-d410-11eb-b8bc-0242ac130003'::uuid);
INSERT INTO SPLIT_EXP_USER_GROUP_MAPPING VALUES (gen_random_uuid(), '65ecee96-d411-11eb-b8bc-0242ac130003'::uuid, 'cf7e9716-d410-11eb-b8bc-0242ac130003'::uuid);
INSERT INTO SPLIT_EXP_USER_GROUP_MAPPING VALUES (gen_random_uuid(), '65ecee96-d411-11eb-b8bc-0242ac130003'::uuid, 'd6a8fec8-d410-11eb-b8bc-0242ac130003'::uuid);
INSERT INTO SPLIT_EXP_USER_GROUP_MAPPING VALUES (gen_random_uuid(), '70a32b0c-d411-11eb-b8bc-0242ac130003'::uuid, 'd6a8fec8-d410-11eb-b8bc-0242ac130003'::uuid);
INSERT INTO SPLIT_EXP_USER_GROUP_MAPPING VALUES (gen_random_uuid(), '70a32b0c-d411-11eb-b8bc-0242ac130003'::uuid, 'dead0538-d410-11eb-b8bc-0242ac130003'::uuid);
INSERT INTO SPLIT_EXP_USER_GROUP_MAPPING VALUES (gen_random_uuid(), '70a32b0c-d411-11eb-b8bc-0242ac130003'::uuid, 'e5d20156-d410-11eb-b8bc-0242ac130003'::uuid);