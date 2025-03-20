use wms_db;

show triggers ;

drop trigger if exists trg_outbound_status_approval;
DELIMITER $$
CREATE TRIGGER trg_outbound_status_approval
    AFTER UPDATE ON outbound_table
    FOR EACH ROW
BEGIN
    DECLARE revenue_ida INT;
    -- 출고 상태가 '승인'으로 변경되었는지 확인
    IF NEW.outbound_status = '승인' and OLD.outbound_status <> '승인' THEN

        -- revenue_table에서 product_id에 해당하는 revenue_id 가져오기
        SELECT revenue_id
        INTO revenue_ida
        FROM revenue_table
        WHERE product_id = NEW.product_id
        LIMIT 1;

        -- revenue_history_table에 출고 정보를 삽입
        INSERT INTO revenue_history_table (revenue_id, change_date, revenue_quantity, change_type)
        VALUES (revenue_ida, NOW(), NEW.outbound_amount, '출고');
    END IF;
END$$

DELIMITER ;

select * from outbound_table;
update outbound_table set outbound_status = '승인' where outbound_id = 8; -- 8
select * from revenue_table;
select * from revenue_history_table;
