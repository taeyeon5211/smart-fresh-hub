use wms_db;
DELIMITER $$

CREATE PROCEDURE GetPendingInboundRequests()
BEGIN
    --  "대기" 상태의 입고 요청을 조회하는 SQL
    SELECT i.inbound_id,
           i.inbound_request_date,
           i.inbound_date,
           i.inbound_status,
           i.inbound_amount,
           p.product_name,
           b.business_name,
           a.admin_id
    FROM inbound_table i
             --  제품 테이블과 조인하여 제품명 가져오기
             JOIN product p ON i.product_id = p.product_id
        -- 사업체 테이블과 조인하여 사업체명 가져오기
             JOIN business_table b ON p.business_id = b.business_id
        -- 관리자 테이블과 조인하여 담당 관리자 ID 가져오기
             JOIN admin_table a ON i.admin_id = a.admin_id
    WHERE i.inbound_status = '대기'  -- "대기" 상태인 입고 요청만 조회
    ORDER BY i.inbound_request_date DESC; -- 요청 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE GetAllInboundHistory()
BEGIN
    --  모든 입고 내역을 조회하는 SQL
    SELECT i.inbound_id,
           i.inbound_request_date,
           i.inbound_date,
           i.inbound_status,
           i.inbound_amount,
           p.product_name,
           b.business_name,
           a.admin_id
    FROM inbound_table i
             -- 제품 테이블과 조인하여 제품명 가져오기
             JOIN product p ON i.product_id = p.product_id
        --  사업체 테이블과 조인하여 사업체명 가져오기
             JOIN business_table b ON p.business_id = b.business_id
        --  관리자 테이블과 조인하여 담당 관리자 ID 가져오기
             JOIN admin_table a ON i.admin_id = a.admin_id
    ORDER BY i.inbound_request_date DESC; --  요청 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE GetInboundHistoryByBusiness(IN p_businessId INT)
BEGIN
    --  특정 사업체의 입고 내역을 조회하는 SQL
    SELECT i.inbound_id,
           i.inbound_request_date,
           i.inbound_date,
           i.inbound_status,
           i.inbound_amount,
           p.product_name,
           b.business_name,
           a.admin_id
    FROM inbound_table i
             --  제품 테이블과 조인하여 제품명 가져오기
             JOIN product p ON i.product_id = p.product_id
        --  사업체 테이블과 조인하여 사업체명 가져오기
             JOIN business_table b ON p.business_id = b.business_id
        --  관리자 테이블과 조인하여 담당 관리자 ID 가져오기
             JOIN admin_table a ON i.admin_id = a.admin_id
    WHERE p.business_id = p_businessId  --  특정 사업체의 입고 내역만 조회
    ORDER BY i.inbound_request_date DESC; --  요청 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE GetAllRevenueHistory()
BEGIN
    -- 모든 재고 변경 이력을 조회하는 SQL
    SELECT r.revenue_id,
           r.change_date,
           r.revenue_quantity,
           r.change_type,
           p.product_name,
           b.business_name
    FROM revenue_history_table r
             JOIN revenue_table rt ON r.revenue_id = rt.revenue_id
             JOIN product p ON rt.product_id = p.product_id
             JOIN business_table b ON p.business_id = b.business_id
    ORDER BY r.change_date DESC; --  변경 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;


DELIMITER $$
drop procedure if exists CheckUserExists;
CREATE PROCEDURE CheckUserExists(
    IN p_user_login_id VARCHAR(50),
    IN p_user_password VARCHAR(255)
)
BEGIN


    select *
    from user_table
    where user_login_id = p_user_login_id
      AND user_password = p_user_password;

END $$

DELIMITER ;


use wms_db;
-- 회원 생성 프로시저
delimiter $$
CREATE PROCEDURE InsertUser(
    IN p_user_login_id VARCHAR(50),
    IN p_user_name VARCHAR(30),
    IN p_user_password VARCHAR(255), -- 나중에 해싱하여 디비에 저장하기
    IN p_user_address VARCHAR(255),
    IN p_user_email VARCHAR(100),
    IN p_user_phone VARCHAR(20),
    IN p_user_birth_date DATE,
    IN p_user_type ENUM ('admin', 'client')
    -- created_at 은 자동으로 현재시간 입력됨.
)
BEGIN
    -- 중복된 로그인 아이디 또는 이메일이 있는지 확인하는 로직 추가하기!!

    INSERT INTO user_table (user_login_id, user_name, user_password, user_address, user_email, user_phone,
                            user_birth_date,
                            user_type)
    VALUES (p_user_login_id, p_user_name, p_user_password, p_user_address,
            p_user_email, p_user_phone, p_user_birth_date,
            p_user_type);
END $$

delimiter ;


-- 회원 삭제 프로시저
delimiter $$

CREATE PROCEDURE DeleteUser(
    IN p_user_login_id varchar(50)
)
BEGIN
    -- 콘솔에서 총관리자가 아이디 입력하면 디비에서 있는지 조회하는 로직 추가하기!
    DELETE
    FROM user_table
    where user_login_id = p_user_login_id;
end $$
delimiter ;


-- 회원 조회 프로시저
delimiter $$

CREATE PROCEDURE FindUser(
    IN p_user_login_id varchar(50)
)
BEGIN
    SELECT *
    FROM user_table
    where user_login_id = p_user_login_id;
END $$

delimiter ;

-- 모든 회원 조회 프로시저
delimiter $$

CREATE PROCEDURE FindAllUsers()
BEGIN
    SELECT * -- 모든 컬럼을 반환할 필요가 있나?
    FROM user_table;
END $$

delimiter ;


-- 회원 업데이트 프로시저

drop procedure if exists UpdateUser;
DELIMITER $$

CREATE PROCEDURE UpdateUser(
    IN p_user_login_id VARCHAR(50),
    IN p_update_type INT,
    IN p_new_value VARCHAR(255)
)
BEGIN
    -- 업데이트 타입에 따라 다른 컬럼을 변경
    IF p_update_type = 1 THEN
        UPDATE user_table SET user_password = p_new_value WHERE user_login_id = p_user_login_id;
    ELSEIF p_update_type = 2 THEN
        UPDATE user_table SET user_address = p_new_value WHERE user_login_id = p_user_login_id;
    ELSEIF p_update_type = 3 THEN
        UPDATE user_table SET user_name = p_new_value WHERE user_login_id = p_user_login_id;
    ELSEIF p_update_type = 4 THEN
        UPDATE user_table SET user_email = p_new_value WHERE user_login_id = p_user_login_id;
    ELSEIF p_update_type = 5 THEN
        UPDATE user_table SET user_phone = p_new_value WHERE user_login_id = p_user_login_id;
    ELSEIF p_update_type = 6 THEN
        UPDATE user_table SET user_birth_date = STR_TO_DATE(p_new_value, '%Y-%m-%d') WHERE user_login_id = p_user_login_id;
    ELSEIF p_update_type = 7 THEN
        UPDATE user_table SET user_type = p_new_value WHERE user_login_id = p_user_login_id;

    END IF;
END $$

DELIMITER ;

