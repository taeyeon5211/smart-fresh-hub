package user.controller;

import login.dto.LoginResDTO;
import user.dto.UserDTO;

import java.sql.SQLException;

public interface AdminCont {
    void createUser();
    void findUser();
    void updateUser() throws SQLException;
    void deleteUser() throws SQLException;

    void readMyAccount(LoginResDTO loginedUser);

    void updateMyAccount(LoginResDTO loginedUser) throws SQLException;

    void deleteMyAccount(LoginResDTO loginedUser);
    void readClientBackUpTbl() throws SQLException;


}
