package login.controller;

import login.service.LoginService;

import java.util.Scanner;

public class LoginContImpl implements LoginCont{

    LoginService loginService;

    private static Scanner sc = new Scanner(System.in);

    public LoginContImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 아이디, 비밀번호를 입력받아 서비스계층의 authLogin()으로 넘겨주는 메서드
     */
    @Override
    public void inputLogin() {
        System.out.print("로그인 아이디를 입력하세요: ");
        String loginId = sc.next();

        System.out.print("비밀번호를 입력하세요: ");
        String password = sc.next();

        loginService.authLogin(loginId, password);
    }


}
