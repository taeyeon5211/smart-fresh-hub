package login.controller;

import login.dto.LoginResDTO;
import login.service.LoginService;
import user.controller.AdminCont;
import user.controller.AdminContImpl;
import user.service.UserService;
import user.vo.UserType;

import java.util.Scanner;

public class LoginContImpl implements LoginCont{

    private final LoginService loginService;
    private UserService userService;
    private static Scanner sc = new Scanner(System.in);

    public LoginContImpl(LoginService loginService) {
        this.loginService = loginService;

    }

    public LoginContImpl(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @Override
    public String startLoginPage() {
    // inputLogin 이거나 회원가입 둘중 선택가능
        System.out.println("===== 메인 메뉴 =====");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.print("메뉴를 선택하세요: ");

        String input = sc.nextLine().trim();

        return input;
    }
@Override
    public void createNewAccount() {
        userService.createNewAccount();
    }

    @Override
    public LoginResDTO inputLogin() {
        System.out.print("로그인 아이디를 입력하세요: ");
        String loginId = sc.next();

        System.out.print("비밀번호를 입력하세요: ");
        String password = sc.next();

        LoginResDTO loginResDto = loginService.authLogin(loginId, password);// 엔터 누를 시 사용자 입력값을 받아 서비스로 넘기기

        return loginResDto;
    }

    @Override
    public String checkUserType(LoginResDTO loginResDto) {
        return loginResDto.getUserType();
    }


}
