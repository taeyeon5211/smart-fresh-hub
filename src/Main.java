import area.controller.AreaController;
import auth.Auth;
import diconfig.DiConfig;
import inbound.controller.InboundController;
import login.controller.LoginCont;
import login.controller.LoginContImpl;
import login.dto.LoginResDTO;
import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;
import outbound.controller.OutboundController;
import user.controller.AdminCont;
import user.controller.AdminContImpl;
import user.repository.UserRepo;
import user.repository.UserRepoImpl;
import user.service.UserService;
import user.service.UserServiceImpl;
import view.*;
import warehouse.controller.WareHouseController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws SQLException {
        DiConfig diConfig = new DiConfig();
        LoginCont loginCont = diConfig.getLoginController();
        AdminCont adminCont = diConfig.getAdminController();
        InboundAdminView inboundAdminView = new InboundAdminView();
        OutboundAdminView outboundAdminView = new OutboundAdminView();
        InboundUserView inboundUserView = new InboundUserView();
        OutboundUserView outboundUserView = new OutboundUserView();
        WarehouseView warehouseView = new WarehouseView();
        Scanner scanner = new Scanner(System.in);
        Auth auth = Auth.getinstance();

        boolean startRun = true;
        AtomicBoolean menuAdminRun = new AtomicBoolean(true);
        AtomicBoolean menuUserRun = new AtomicBoolean(true);


        Map<Integer, Runnable> creatAdminMain = new HashMap<>();

        creatAdminMain.put(1, () -> {
                // 총관리자 가 할 수 있는 기능 시작
                adminCont.startAdminMenu();
        }); // 로그인 어드민으로 시작
        creatAdminMain.put(2, warehouseView::run);
        creatAdminMain.put(3, inboundAdminView::run);
        creatAdminMain.put(4, outboundAdminView::run);
        creatAdminMain.put(5, () -> {
            menuAdminRun.set(false);
        });




        Map<Integer, Runnable> creatUserMain = new HashMap<>();

        creatUserMain.put(1,() -> {
            adminCont.startClientMenu();
        });
        creatUserMain.put(2, inboundUserView::run);
        creatUserMain.put(3, outboundUserView::run);
        creatUserMain.put(4, () -> menuUserRun.set(false));

        // 프로그램 시작!
        while(startRun) {
            String input = loginCont.startLoginPage();
            switch (input) {
                case "1": {
                    loginCont.inputLogin();
                    String userType = loginCont.checkUserType(auth.getLoginResDto());
                    System.out.println(userType);
                    switch (userType) {
                        case "ADMIN": {
                            System.out.println("관리자모드 입니다.");
                            while (menuAdminRun.get()) {
                                System.out.println("1. 회원관리 관리자모드, 2번 창고관리 관리자모드, 3번 입고관리 관리자모드 , 4번 출고관리 관리자모드, 5번 종료");
                                int selectNum = scanner.nextInt();
                                scanner.nextLine();
                                creatAdminMain.get(selectNum).run();
                            }
                        }
                        case "CLIENT": {
                            System.out.println("유저모드 입니다.");
                            while (menuUserRun.get()) {
                                System.out.println("1. 내정보관리, 2. 입고관리 , 3. 출고관리, 4. 종료");
                                int selectNum = scanner.nextInt();
                                scanner.nextLine();
                                creatUserMain.get(selectNum).run();
                            }
                        }
                    }
                }
                break;
                case "2":
                    loginCont.createNewAccount();
                    break;
                    case "3": startRun = false;
            }
        }
    }
}