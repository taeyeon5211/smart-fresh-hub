package user.controller;

import login.dto.LoginResDTO;
import user.dto.UserDTO;

import java.sql.SQLException;

public interface AdminCont {
    void createUser();
    void findUser();

    /**
     * 데이터베이스의 모든 회원 객체를 불러오는 메서드
     */
    void fetchAllUsers();
    void updateUser();
    void deleteUser();

    void readMyAccount(LoginResDTO loginedUser);

    void updateMyAccount(LoginResDTO loginedUser);

    void deleteMyAccount(LoginResDTO loginedUser);
    void readClientBackUpTbl();

    void startAdminMenu();
    void startClientMenu();
}
