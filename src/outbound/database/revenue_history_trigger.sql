use wms_db;

show triggers ;

drop trigger if exists trg_outbound_status_approval;
DELIMITER $$
CREATE TRIGGER trg_outbound_status_approval
    AFTER UPDATE ON outbound_table
    FOR EACH ROW
BEGIN
    DECLARE revenue_ida INT;
    DECLARE revenue_amount1 INT;
    -- 출고 상태가 '승인'으로 변경되었는지 확인
    IF NEW.outbound_status = '승인' THEN

        -- revenue_table에서 product_id에 해당하는 revenue_id 가져오기
        SELECT revenue_id
        INTO revenue_ida
        FROM revenue_table
        WHERE product_id = NEW.product_id
        LIMIT 1;

        SELECT revenue_amount
        INTO revenue_amount1
        FROM revenue_table
        WHERE product_id = NEW.product_id
        LIMIT 1;

        IF revenue_amount1 > NEW.outbound_amount THEN
            INSERT INTO revenue_history_table (revenue_id, revenue_quantity, change_type)
            VALUES (revenue_ida, revenue_amount1 - NEW.outbound_amount, '출고');
        END IF;
    END IF;
END$$
DELIMITER ;
-- revenue_table after update trigger 존재 -> revenue_table after update trigger 생성불가
-- outbound_status 가 '승인' 으로 바뀌고 revenue_amount 가 outbound_amount 보다 클때
-- (revenue_amount - outbound_amount) 값을 revenue_history_table 에 insert
