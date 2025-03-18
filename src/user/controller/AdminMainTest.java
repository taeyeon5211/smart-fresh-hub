package user.controller;

import user.repository.UserRepo;
import user.repository.UserRepoImpl;
import user.service.UserService;
import user.service.UserServiceImpl;

/**
 * 테스트용 클래스입니다.
 */
public class AdminMainTest {
    public static void main(String[] args) {
        UserRepo userRepo = new UserRepoImpl();
        UserService userService = new UserServiceImpl(userRepo);
        AdminCont adminCont = new AdminContImpl(userService);

        AdminMainContImpl adminMainCont = new AdminMainContImpl(adminCont);
        adminMainCont.start();
    }
}
