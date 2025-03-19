package login.controller;

import login.dto.LoginResDTO;
import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;
import user.controller.AdminMainTest;

public class Main_Test {
    public static void main(String[] args) {
        LoginRepo loginRepo = new LoginRepoImpl();
        LoginService loginService = new LoginServiceImpl(loginRepo);
        LoginContImpl loginCont = new LoginContImpl(loginService);

       LoginResDTO loginResDto =  loginCont.inputLogin();
        if(loginResDto != null) {// 널 아니면 회원관리 시작
            System.out.println(loginResDto.getUserLoginId()+ "사용자 확인되었습니다.회원관리 시작합니다.");
        }
    }
}
