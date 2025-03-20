package login.controller;

import login.dto.LoginResDTO;
import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;
import user.controller.*;
import user.repository.UserRepo;
import user.repository.UserRepoImpl;
import user.service.UserService;
import user.service.UserServiceImpl;
import user.vo.UserType;

import java.sql.SQLException;

public class Main_Test {
    public static void main(String[] args) throws SQLException {
        LoginRepo loginRepo = new LoginRepoImpl();
        LoginService loginService = new LoginServiceImpl(loginRepo);
        LoginCont loginCont = new LoginContImpl(loginService);

        UserRepo userRepo = new UserRepoImpl();
        UserService userService = new UserServiceImpl(userRepo);
        AdminCont adminCont = new AdminContImpl(userService, loginCont);



        loginCont = new LoginContImpl(loginService, userService);
        // 로그인 시작
        String input = loginCont.startLoginPage();


        if(input.equals("1")){
            LoginResDTO loginResDto = loginCont.inputLogin();
            String userType = loginCont.checkUserType(loginResDto);
            if (loginResDto != null) {// 널 아니면 회원관리 시작

                System.out.println(loginResDto.getUserLoginId() + " 사용자 확인되었습니다. " + userType + " 유형입니다. " + userType + " 전용 메뉴 시작합니다. ");
                if (userType.equals("ADMIN")) {
                    // 총관리자 가 할 수 있는 기능 시작
                    adminCont.startAdminMenu();
                } else {
                    adminCont.startClientMenu(loginResDto);
                }
        }

        }else if (input.equals("2")){
            loginCont.createNewAccount();
        }
    }
}
