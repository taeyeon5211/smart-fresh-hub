package user.controller;

import login.dto.LoginResDTO;
import user.dto.UserDTO;

import java.sql.SQLException;

public interface AdminCont {
    void createUser();
    void findUser();
    void updateUser();
    void deleteUser();

    void readMyAccount(LoginResDTO loginedUser);

    void updateMyAccount(LoginResDTO loginedUser);

    void deleteMyAccount(LoginResDTO loginedUser);
    void readClientBackUpTbl();

    void startAdminMenu();
    void startClientMenu();
}
