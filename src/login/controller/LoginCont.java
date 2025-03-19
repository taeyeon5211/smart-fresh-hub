package login.controller;


import login.dto.LoginResDTO;
import user.vo.UserType;

public interface LoginCont {
    LoginResDTO inputLogin();
    String checkUserType(LoginResDTO loginResDto);
}
