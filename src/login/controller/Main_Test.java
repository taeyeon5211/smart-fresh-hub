package login.controller;

import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;

public class Main_Test {
    public static void main(String[] args) {
        LoginRepo loginRepo = new LoginRepoImpl();
        LoginService loginService = new LoginServiceImpl(loginRepo);
        LoginContImpl loginCont = new LoginContImpl(loginService);

        loginCont.inputLogin();
    }
}
