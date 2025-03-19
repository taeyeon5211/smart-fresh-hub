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


-- 회원이 삭제될때 backup_deleted_user에 기록하기
CREATE TRIGGER backup_deleted_user
    BEFORE DELETE
    ON user_table
    FOR EACH ROW
BEGIN
    INSERT INTO user_backup_table (user_id, user_login_id, user_name, user_password, user_address,
                                   user_email, user_phone, user_birth_date, user_created_at, user_type, deleted_at)
    VALUES (OLD.user_id, OLD.user_login_id, OLD.user_name, OLD.user_password, OLD.user_address,
            OLD.user_email, OLD.user_phone, OLD.user_birth_date, OLD.user_created_at, OLD.user_type, NOW());
END;

