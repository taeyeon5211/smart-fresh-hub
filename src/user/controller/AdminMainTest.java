package user.controller;

import login.controller.LoginCont;
import login.controller.LoginContImpl;
import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;
import user.repository.UserRepo;
import user.repository.UserRepoImpl;
import user.service.UserService;
import user.service.UserServiceImpl;

import java.sql.SQLException;

/**
 * 테스트용 클래스입니다.
 */
public class AdminMainTest {
    public static void main(String[] args) throws SQLException {

        LoginRepo loginRepo = new LoginRepoImpl();
        LoginService loginService = new LoginServiceImpl(loginRepo);
        LoginCont loginCont = new LoginContImpl(loginService);

        UserRepo userRepo = new UserRepoImpl();
        UserService userService = new UserServiceImpl(userRepo);
        AdminCont adminCont = new AdminContImpl(userService, loginCont);


        AdminMainContImpl adminMainCont = new AdminMainContImpl(adminCont);
        adminMainCont.startAdminMenu();


    }
}
