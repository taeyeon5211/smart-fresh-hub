DELIMITER $$

CREATE PROCEDURE CheckUserExists(
    IN p_user_login_id VARCHAR(50),
    IN p_user_password VARCHAR(255),
    OUT p_login_success INT
)
BEGIN
    SET p_login_success = 0; -- 매칭되는 회원 0 명으로 시작

    select count(*)
    into p_login_success
    from user_table
    where user_login_id = p_user_login_id
      AND user_password = p_user_password;

    IF p_login_success = 1 THEN
        SET p_login_success = 1;

    end if;
END $$

DELIMITER ;
