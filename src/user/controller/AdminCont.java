package user.controller;

import user.dto.UserDTO;

import java.sql.SQLException;

public interface AdminCont {
    void createUser();
    void findUser();
    void updateUser() throws SQLException;
    void deleteUser();
}
