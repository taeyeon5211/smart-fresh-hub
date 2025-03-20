package auth;

import diconfig.DiConfig;
import login.controller.LoginCont;
import login.dto.LoginResDTO;

public class Auth {
    private static final Auth auth = new Auth();

    private Auth() {};
    public static Auth getinstance(){
        return Auth.auth;
    }
    private LoginResDTO loginCont;

    public LoginResDTO getLoginResDto() {
        return loginCont;
    }

    public void setLoginResDto(LoginResDTO loginCont) {
        this.loginCont = loginCont;
    }
}
