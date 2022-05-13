-- create admit
INSERT INTO marketplace_user (user_id, password)
VALUES('admin', '123456');
INSERT INTO marketplace_admin (user_id)
VALUES('admin');

-- create brands
INSERT INTO marketplace_user (user_id, password)
VALUES ('Brand01', '123456');
INSERT INTO brand (user_id, name, address, join_date)
VALUES ('Brand01', 'Brand X', '503 Rolling Creek Dr Austin, AR', '1 APR 2021');


INSERT INTO marketplace_user (user_id, password)
VALUES ('Brand02', '123456');
INSERT INTO brand (user_id, name, address, join_date)
VALUES ('Brand02', 'Brand Y', '939 Orange Ave Coronado, CA', '25 MAR 2021');


INSERT INTO marketplace_user (user_id, password)
VALUES ('Brand03', '123456');
INSERT INTO brand (user_id, name, address, join_date)
VALUES ('Brand03', 'Brand Z', '20 Roszel Rd Princeton, NJ', '8 MAY 2021');


-- insert loyalty programs
INSERT INTO loyalty_program (code, type, name, brand_guid, status)
VALUES ('TLP01', 'tiered', 'SportGoods', 'Brand01', 'Active');


INSERT INTO loyalty_program (code, type, name, brand_guid, status)
VALUES ('TLP02', 'tiered', 'MegaCenter', 'Brand02', 'Active');


INSERT INTO loyalty_program (code, type, name, brand_guid, status)
VALUES ('RLP01', 'regular', 'TechSups', 'Brand03', 'Active');


-- insert tiers
INSERT INTO tier (lp_code, name, tier_level, points_required, multiplier)
VALUES ('TLP01', 'Bronze', 0, 0, 2);

INSERT INTO tier (lp_code, name, tier_level, points_required, multiplier)
VALUES ('TLP01', 'Silver', 1, 170, 3);

INSERT INTO tier (lp_code, name, tier_level, points_required, multiplier)
VALUES ('TLP01', 'Gold', 2, 270, 4);


INSERT INTO tier (lp_code, name, tier_level, points_required, multiplier)
VALUES ('TLP02', 'Special', 0, 0, 2);

INSERT INTO tier (lp_code, name, tier_level, points_required, multiplier)
VALUES ('TLP02', 'Premium', 1, 210, 3);


-- create activity categories
INSERT INTO activity_category (activity_category_code, name)
VALUES ('A01', 'Purchase');

INSERT INTO activity_category (activity_category_code, name)
VALUES ('A02', 'Write a review');

INSERT INTO activity_category (activity_category_code, name)
VALUES ('A03', 'Refer a friend');


-- create reward categories
INSERT INTO reward_category (reward_category_code, name)
VALUES ('R01', 'Gift Card');

INSERT INTO reward_category (reward_category_code, name)
VALUES ('R02', 'Free Product');


-- enroll brands in activity and reward categories
INSERT INTO lp_activity_category (activity_category_code, lp_code)
VALUES ('A01', 'TLP01');
INSERT INTO lp_activity_category (activity_category_code, lp_code)
VALUES ('A02', 'TLP01');

INSERT INTO lp_reward_category (reward_category_code, lp_code)
VALUES ('R01', 'TLP01');
INSERT INTO lp_reward_category (reward_category_code, lp_code)
VALUES ('R02', 'TLP01');


INSERT INTO lp_activity_category (activity_category_code, lp_code)
VALUES ('A01', 'TLP02');
INSERT INTO lp_activity_category (activity_category_code, lp_code)
VALUES ('A03', 'TLP02');

INSERT INTO lp_reward_category (reward_category_code, lp_code)
VALUES ('R01', 'TLP02');
INSERT INTO lp_reward_category (reward_category_code, lp_code)
VALUES ('R02', 'TLP02');


INSERT INTO lp_activity_category (activity_category_code, lp_code)
VALUES ('A03', 'RLP01');

INSERT INTO lp_reward_category (reward_category_code, lp_code)
VALUES ('R01', 'RLP01');


-- insert reward earning rules
INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('REB1N1','TLP01', 15, 1);
INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code)
VALUES ('REB1N1', 'TLP01', 'A01');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('REB1N2','TLP01', 10, 1);
INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code)
VALUES ('REB1N2', 'TLP01', 'A02');


INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('REB2N1','TLP02', 40, 1);
INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code)
VALUES ('REB2N1', 'TLP02', 'A01');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('REB2N2','TLP02', 30, 1);
INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code)
VALUES ('REB2N2', 'TLP02', 'A03');


INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('REB3N1', 'RLP01', 10, 1);
INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code)
VALUES ('REB3N1', 'RLP01', 'A03');


-- insert reward redeeming rules
INSERT INTO reward (reward_id, lp_code, reward_category_code, num_instances)
VALUES (1, 'TLP01', 'R01', 40);
INSERT INTO gift_card (reward_id, lp_code, amount, expiry_date)
VALUES (1, 'TLP01', 123.45, '12 DEC 2021');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('RRB1N1','TLP01', 80, 1);
INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code)
VALUES ('RRB1N1', 'TLP01', 'R01');

INSERT INTO reward (reward_id, lp_code, reward_category_code, num_instances)
VALUES (2, 'TLP01', 'R02', 25);
INSERT INTO free_product (reward_id, lp_code, product_name)
VALUES (2, 'TLP01', 'Toy Car');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('RRB1N2','TLP02', 70, 1);
INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code)
VALUES ('RRB1N2', 'TLP02', 'R02');


INSERT INTO reward (reward_id, lp_code, reward_category_code, num_instances)
VALUES (1, 'TLP02', 'R01', 30);
INSERT INTO gift_card (reward_id, lp_code, amount, expiry_date)
VALUES (1, 'TLP02', 123.45, '12 DEC 2021');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('RRB2N1', 'TLP02', 120, 1);
INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code)
VALUES ('RRB2N1', 'TLP02', 'R01');

INSERT INTO reward (reward_id, lp_code, reward_category_code, num_instances)
VALUES (2, 'TLP02', 'R02', 50);
INSERT INTO free_product (reward_id, lp_code, product_name)
VALUES (2, 'TLP02', 'Toy Car');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('RRB2N2','TLP02', 90, 1);
INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code)
VALUES ('RRB2N2', 'TLP02', 'R02');


INSERT INTO reward (reward_id, lp_code, reward_category_code, num_instances)
VALUES (1, 'RLP01', 'R01', 25);
INSERT INTO gift_card (reward_id, lp_code, amount, expiry_date)
VALUES (1, 'RLP01', 123.45, '12 DEC 2021');

INSERT INTO reward_rule (rule_code, lp_code, num_points, version)
VALUES ('RRB3N1', 'RLP01', 100, 1);
INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code)
VALUES ('RRB3N1', 'RLP01', 'R01');


-- insert customer C0001
INSERT INTO marketplace_user (user_id, password)
VALUES ('C0001', '123456');
INSERT INTO customer (user_id, name, phone, address, wallet_id)
VALUES ('C0001', 'Peter Parker', 8636368778, '20 Ingram Street, NY', 'W0001');
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0001', 'TLP01', 0, 0);
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0001', 'TLP02', 0, 0);


INSERT INTO marketplace_user (user_id, password)
VALUES ('C0002', '123456');
INSERT INTO customer (user_id, name, phone, address, wallet_id)
VALUES ('C0002', 'Steve Rogers', 8972468552, '569 Leaman Place, NY', 'W0002');
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0002', 'TLP01', 0, 0);


INSERT INTO marketplace_user (user_id, password)
VALUES ('C0003', '123456');
INSERT INTO customer (user_id, name, phone, address, wallet_id)
VALUES ('C0003', 'Diana Prince', 8547963210, '1700 Broadway St, NY', 'W0003');
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0003', 'TLP02', 0, 0);
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0003', 'RLP01', 0, 0);


INSERT INTO marketplace_user (user_id, password)
VALUES ('C0004', '123456');
INSERT INTO customer (user_id, name, phone, address, wallet_id)
VALUES ('C0004', 'Billy Batson', 8974562583, '5015 Broad St, Philadelphia, PA', 'W0004');


INSERT INTO marketplace_user (user_id, password)
VALUES ('C0005', '123456');
INSERT INTO customer (user_id, name, phone, address, wallet_id)
VALUES ('C0005', 'Tony Stark', 8731596464, '10880 Malibu Point, CA', 'W0005');
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0005', 'TLP01', 0, 0);
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0005', 'TLP02', 0, 0);
INSERT INTO customer_enrollment(customer_id, lp_code, num_points, tier_level)
VALUES ('C0005', 'RLP01', 0, 0);


-- insert activities
INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (1, 'C0001', 'A01', '10 JUN 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (1, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 1, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (2, 'C0001', 'A01', '10 JUN 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (2, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 2, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (3, 'C0001', 'A01', '10 JUN 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (3, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 3, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (4, 'C0001', 'A01', '10 JUN 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (4, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 4, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (5, 'C0001', 'A02', '18 JUN 2021');
INSERT INTO review (activity_id, content)
VALUES (5, 'It was ok');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 5, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (6, 'C0001', 'A02', '18 JUN 2021');
INSERT INTO review (activity_id, content)
VALUES (6, 'It was ok');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 6, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (7, 'C0001', 'A01', '9 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (7, 100);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 7, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (8, 'C0001', 'A01', '9 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (8, 100);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 8, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (9, 'C0001', 'A01', '15 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (9, 200);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 9, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (10, 'C0001', 'A03', '3 OCT 2021');
INSERT INTO friend_reference (activity_id)
VALUES (10);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 10, 'TLP02', 30);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (11, 'C0001', 'A03', '18 OCT 2021');
INSERT INTO friend_reference (activity_id)
VALUES (11);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 11, 'TLP02', 30);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (12, 'C0001', 'A03', '18 OCT 2021');
INSERT INTO friend_reference (activity_id)
VALUES (12);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0001', 12, 'TLP02', 30);


INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (13, 'C0002', 'A01', '2 JUL 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (13, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0002', 13, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (14, 'C0002', 'A01', '2 JUL 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (14, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0002', 14, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (15, 'C0002', 'A01', '8 JUL 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (15, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0002', 15, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (16, 'C0002', 'A01', '8 JUL 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (16, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0002', 16, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (17, 'C0002', 'A02', '5 AUG 2021');
INSERT INTO review (activity_id, content)
VALUES (17, 'It was great');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0002', 17, 'TLP01', 10);


INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (18, 'C0003', 'A03', '30 JUL 2021');
INSERT INTO friend_reference (activity_id)
VALUES (18);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 18, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (19, 'C0003', 'A03', '30 JUL 2021');
INSERT INTO friend_reference (activity_id)
VALUES (19);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 19, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (20, 'C0003', 'A03', '30 JUL 2021');
INSERT INTO friend_reference (activity_id)
VALUES (20);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 20, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (21, 'C0003', 'A03', '30 JUL 2021');
INSERT INTO friend_reference (activity_id)
VALUES (21);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 21, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (22, 'C0003', 'A01', '1 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (22, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 22, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (23, 'C0003', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (23, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 23, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (24, 'C0003', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (24, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 24, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (25, 'C0003', 'A01', '2 SEP 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (25, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 25, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (26, 'C0003', 'A03', '1 OCT 2021');
INSERT INTO friend_reference (activity_id)
VALUES (26);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 26, 'TLP02', 30);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (27, 'C0003', 'A03', '16 OCT 2021');
INSERT INTO friend_reference (activity_id)
VALUES (27);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0003', 27, 'TLP02', 30);


INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (28, 'C0005', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (28, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 28, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (29, 'C0005', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (29, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 29, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (30, 'C0005', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (30, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 30, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (31, 'C0005', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (31, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 31, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (32, 'C0005', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (32, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 32, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (33, 'C0005', 'A01', '10 AUG 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (33, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 33, 'TLP01', 15);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (34, 'C0005', 'A03', '16 SEP 2021');
INSERT INTO friend_reference (activity_id)
VALUES (34);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 34, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (35, 'C0005', 'A03', '16 SEP 2021');
INSERT INTO friend_reference (activity_id)
VALUES (35);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 35, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (36, 'C0005', 'A03', '16 SEP 2021');
INSERT INTO friend_reference (activity_id)
VALUES (36);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 36, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (37, 'C0005', 'A03', '30 SEP 2021');
INSERT INTO friend_reference (activity_id)
VALUES (37);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 37, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (38, 'C0005', 'A03', '30 SEP 2021');
INSERT INTO friend_reference (activity_id)
VALUES (38);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 38, 'RLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (39, 'C0005', 'A02', '30 SEP 2021');
INSERT INTO review (activity_id, content)
VALUES (39, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 39, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (40, 'C0005', 'A02', '30 SEP 2021');
INSERT INTO review (activity_id, content)
VALUES (40, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 40, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (41, 'C0005', 'A02', '30 SEP 2021');
INSERT INTO review (activity_id, content)
VALUES (41, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 41, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (42, 'C0005', 'A02', '30 SEP 2021');
INSERT INTO review (activity_id, content)
VALUES (42, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 42, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (43, 'C0005', 'A02', '30 SEP 2021');
INSERT INTO review (activity_id, content)
VALUES (43, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 43, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (44, 'C0005', 'A01', '10 OCT 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (44, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 44, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (45, 'C0005', 'A01', '10 OCT 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (45, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 45, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (46, 'C0005', 'A01', '10 OCT 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (46, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 46, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (47, 'C0005', 'A01', '10 OCT 2021');
INSERT INTO purchase (activity_id, amount)
VALUES (47, 123);
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 47, 'TLP02', 40);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (48, 'C0005', 'A02', '15 OCT 2021');
INSERT INTO review (activity_id, content)
VALUES (48, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 48, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (49, 'C0005', 'A02', '15 OCT 2021');
INSERT INTO review (activity_id, content)
VALUES (49, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 49, 'TLP01', 10);

INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date)
VALUES (50, 'C0005', 'A02', '15 OCT 2021');
INSERT INTO review (activity_id, content)
VALUES (50, 'It was fantastic');
INSERT INTO wallet_activity (wallet_id, activity_id, lp_code, points)
VALUES ('W0005', 50, 'TLP01', 10);


INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0001', 1, 'TLP01', 80, 1, '2 JUL 2021');

INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0001', 1, 'TLP02', 120, 1, '25 AUG 2021');

INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0001', 2, 'TLP02', 90, 1, '20 OCT 2021');


INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0002', 2, 'TLP01', 70, 1, '1 SEP 2021');


INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0003', 2, 'TLP02', 90, 1, '26 AUG 2021');

INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0003', 2, 'TLP02', 90, 1, '18 OCT 2021');


INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0005', 1, 'TLP02', 120, 1, '11 OCT 2021');

INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0005', 1, 'TLP01', 80, 1, '11 OCT 2021');

INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date)
VALUES ('C0005', 2, 'TLP01', 70, 1, '17 OCT 2021');