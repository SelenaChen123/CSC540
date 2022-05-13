-- 1. List all customers that are not part of Brand02's program.

SELECT *
FROM customer C
WHERE NOT EXISTS (
    SELECT *
    FROM customer_enrollment CE, loyalty_program L
    WHERE CE.customer_id = C.user_id AND CE.lp_code = L.code AND L.brand_guid = 'Brand02'
)


-- 2. List customers that have joined a loyalty program but have not participated in any activity in that program (list the customerid and the loyalty program id).

SELECT CE.customer_id, CE.lp_code
FROM customer_enrollment CE
WHERE NOT EXISTS (
    SELECT *
    FROM wallet_activity WA
    WHERE WA.lp_code = CE.lp_code AND EXISTS (
        SELECT C.user_id
        FROM customer C
        WHERE CE.customer_id = C.user_id AND C.wallet_id = WA.wallet_id
    )
)


-- 3. List the rewards that are part of Brand01 loyalty program.

SELECT *
FROM reward R
WHERE EXISTS (
    SELECT *
    FROM loyalty_program L
    WHERE R.lp_code = L.code AND L.brand_guid = 'Brand01'
)


-- 4. List all the loyalty programs that include “refer a friend” as an activity in at least one of their reward rules.

SELECT *
FROM loyalty_program L
WHERE EXISTS (
    SELECT RER.rule_code
    FROM reward_earning_rule RER, activity_category AC
    WHERE RER.activity_category_code = AC.activity_category_code AND AC.name = 'Refer a friend' AND L.code IN (
        SELECT RR.lp_code
        FROM reward_rule RR
        WHERE RR.rule_code = RER.rule_code
    )
)


-- 5. For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.

SELECT A.activity_category_code, COUNT(*)
FROM activity A
WHERE EXISTS (
    SELECT WA.activity_id
    FROM wallet_activity WA, loyalty_program L
    WHERE WA.activity_id = A.activity_id AND L.code = WA.lp_code AND L.brand_guid = 'Brand01'
)
GROUP BY A.activity_category_code


-- 6. List customers of Brand01 that have redeemed at least twice.

SELECT C.user_id
FROM customer C
WHERE 2 <= (
    SELECT COUNT(*)
    FROM redeemed_reward RR, loyalty_program L
    WHERE RR.customer_id = C.user_id AND RR.lp_code = L.code AND L.brand_guid = 'Brand01'
)


-- 7. All brands where total number of points redeemed overall is less than 500 points

SELECT *
FROM brand B
WHERE NOT EXISTS (
    SELECT RR.lp_code
    FROM loyalty_program L, redeemed_reward RR
    WHERE L.brand_guid = B.user_id AND RR.lp_code = L.code
) OR 500 > (
    SELECT SUM(RR.num_points)
    FROM loyalty_program L, redeemed_reward RR
    WHERE L.brand_guid = B.user_id AND RR.lp_code = L.code
)


-- 8. For Customer C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021.

SELECT COUNT(*)
FROM wallet_activity WA
WHERE WA.wallet_id IN (
    SELECT C.wallet_id
    FROM customer C
    WHERE C.user_id = 'C0003'
) AND WA.lp_code IN (
    SELECT L.code
    FROM loyalty_program L
    WHERE L.brand_guid = 'Brand02'
) AND WA.activity_id IN (
    SELECT A.activity_id
    FROM activity A
    WHERE A.activity_date >= '1 AUG 2021' AND A.activity_date <= '30 SEP 2021'
)

-- Double check this date comparison syntax works in oracle
-- Need redemption activities too?
