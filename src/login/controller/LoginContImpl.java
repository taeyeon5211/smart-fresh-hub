package login.controller;

import login.dto.LoginResDTO;
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

        LoginResDTO resDto = loginService.authLogin(loginId, password);
        if (resDto != null) {
            // response 가 널이 아니면 관리자인지 아닌지 체크하기
        }

    }

    /**
     * 로그인 한 회원이 총관리자인지 회원인지 판단하는 메서드
     */
    @Override
    public String checkUserType(LoginResDTO resDto) {
        if (resDto.getUserType().equals("admin")) {
            return "admin";
        } else {
            return "user";
        }
    }

}
