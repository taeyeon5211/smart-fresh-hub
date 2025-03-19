use wms_db;
DELIMITER $$

CREATE TRIGGER trg_revenue_inbound
    AFTER INSERT ON revenue_table  -- `revenue_table`에 새로운 데이터(`INSERT`)가 추가된 후 실행
    FOR EACH ROW
BEGIN
    -- `revenue_history_table`에 "입고" 기록 추가
    INSERT INTO revenue_history_table (revenue_id, revenue_quantity, change_type)
    VALUES (NEW.revenue_id, NEW.revenue_amount, '입고');
END $$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER trg_revenue_update_inbound
    AFTER UPDATE ON revenue_table  -- `revenue_table`의 데이터(`UPDATE`)가 변경된 후 실행
    FOR EACH ROW
BEGIN
    -- 기존 재고보다 새로운 재고 수량이 많을 때만 "입고" 기록
    IF NEW.revenue_amount > OLD.revenue_amount THEN
        INSERT INTO revenue_history_table (revenue_id, revenue_quantity, change_type)
        VALUES (NEW.revenue_id, NEW.revenue_amount - OLD.revenue_amount, '입고');
    END IF;
END $$

DELIMITER ;

