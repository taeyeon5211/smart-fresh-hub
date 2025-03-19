package login.controller;

import login.service.LoginService;

import java.util.Scanner;

public class LoginContImpl implements LoginCont{

    LoginService loginService;

    private static Scanner sc = new Scanner(System.in);

    public LoginContImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void inputLogin() {
        System.out.print("로그인 아이디를 입력하세요: ");
        String loginId = sc.next();

        System.out.print("비밀번호를 입력하세요: ");
        String password = sc.next();

        loginService.authLogin(loginId, password);// 엔터 누를 시 사용자 입력값을 받아 서비스로 넘기기
    }


}
