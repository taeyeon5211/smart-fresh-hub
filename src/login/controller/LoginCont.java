package login.controller;
import login.dto.LoginResDTO;


public interface LoginCont {
    void inputLogin();
    String checkUserType(LoginResDTO loginResDto);
    String startLoginPage();
    void createNewAccount();
}
