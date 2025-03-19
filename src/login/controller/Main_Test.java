package login.controller;

import login.dto.LoginResDTO;
import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;
import user.controller.AdminMainContImpl;
import user.controller.AdminMainTest;
import user.vo.UserType;

public class Main_Test {
    public static void main(String[] args) {
        LoginRepo loginRepo = new LoginRepoImpl();
        LoginService loginService = new LoginServiceImpl(loginRepo);
        LoginContImpl loginCont = new LoginContImpl(loginService);

       LoginResDTO loginResDto =  loginCont.inputLogin();
       String userType = loginCont.checkUserType(loginResDto);
        if(loginResDto != null) {// 널 아니면 회원관리 시작

                System.out.println(loginResDto.getUserLoginId()+ " 사용자 확인되었습니다." + userType + " 유형입니다." + userType + " 전용 메뉴 시작합니다.");
                if(userType.equals("admin")) {
                    // 총관리자 가 할 수 있는 기능 시작
                }


        }
    }
}
