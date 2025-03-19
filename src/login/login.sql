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


