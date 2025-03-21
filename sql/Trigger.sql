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
select * from user_table;
-- 회원이 삭제될때 backup_deleted_user에 기록하기
CREATE TRIGGER backup_deleted_user
    BEFORE DELETE
    ON user_table
    FOR EACH ROW
BEGIN
    INSERT INTO user_backup_table (user_id, user_login_id, user_name, user_created_at, deleted_at)
    VALUES (OLD.user_id, OLD.user_login_id, OLD.user_name, OLD.user_created_at,  NOW());
END;


show triggers ;


-- 출고상태가 승인으로 바뀌면 재고 히스토리 테이블에 자동으로 입력하는 트리거
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

