select * from revenue_table;

select * from outbound_table;

select * from outbound_table where product_id in (select product_id from product where business_id = 2);

UPDATE revenue_table r
    JOIN product p ON r.product_id = p.product_id
    JOIN outbound_table o ON o.product_id = p.product_id
SET r.revenue_amount = r.revenue_amount - o.outbound_amount
WHERE o.outbound_status = '승인'
  and outbound_id = 11
  AND r.revenue_amount >= o.outbound_amount; -- 재고 부족 시 업데이트되지 않도록 조건 추가

UPDATE revenue_table r
    JOIN product p ON r.product_id = p.product_id
    JOIN outbound_table o ON o.product_id = p.product_id
SET r.revenue_amount = r.revenue_amount - o.outbound_amount
WHERE o.outbound_status = '승인'
  and business_id = 2
  AND r.revenue_amount >= o.outbound_amount; -- 재고 부족 시 업데이트되지 않도록 조건 추가

select * from revenue_table where product_id = 2;

select * from revenue_history_table;
-- 승인된 출고목록
select distinct r.revenue_amount, o.outbound_id, o.outbound_amount, p.business_id from revenue_table r
    JOIN product p ON r.product_id = p.product_id
    JOIN outbound_table o ON o.product_id = p.product_id
WHERE o.outbound_status = '승인';

SELECT distinct r.revenue_amount FROM revenue_table r
    JOIN product p ON r.product_id = p.product_id
    JOIN outbound_table o ON o.product_id = p.product_id
WHERE o.outbound_id = 1;

select * from revenue_table;
delete from revenue_table where revenue_amount = 0;

select * from outbound_table where outbound_status = '대기';
update outbound_table set outbound_status = '승인' where outbound_id = 1;
select * from revenue_history_table;

select * from business_table;

select * from outbound_table;
delete from outbound_table where outbound_id = 12;