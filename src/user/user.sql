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

