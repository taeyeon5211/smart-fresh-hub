import area.controller.AreaController;
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

        Map<Integer, Runnable> createmain = new HashMap<>();

        createmain.put(1, () -> {
                // 총관리자 가 할 수 있는 기능 시작
                adminCont.startAdminMenu();
        }); // 로그인 어드민으로 시작
//        createmain.put(2, () -> {
//            String input = loginCont.startLoginPage();
//            LoginResDTO loginResDto = loginCont.inputLogin();
//            adminCont.startClientMenu(loginResDto);
//        }); // 로그인 후에 일반회원 회원관리 실행
//        createmain.put(3, () -> loginCont.createNewAccount());
        createmain.put(2, warehouseView::run);
        createmain.put(3, inboundAdminView::run);
//        createmain.put(6, inboundUserView::run);
        createmain.put(4, outboundAdminView::run);
//        createmain.put(8, outboundUserView::run);


        // 프로그램 시작!
        String input = loginCont.startLoginPage();
        switch (input) {
            case "1":{
                LoginResDTO loginResDto = loginCont.inputLogin();
                String userType = loginCont.checkUserType(loginResDto);
                System.out.println(userType);
                switch (userType) {
                    case "ADMIN" : {
                        System.out.println("관리자모드 입니다.");
                        while(true) {
                            System.out.println("1. 회원관리 관리자모드, 2번 창고관리 관리자모드, 3번 입고관리 관리자모드 , 4번 출고관리 관리자모드");
                            int selectNum = scanner.nextInt();
                            scanner.nextLine();
                            createmain.get(selectNum).run();
                        }

                    }
                    case "CLIENT" : {

                    } break;
                }
            }
                break;
            case "2":
                loginCont.createNewAccount();
                break;
        }
    }
}