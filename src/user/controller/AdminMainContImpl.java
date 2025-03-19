package user.controller;

import login.dto.LoginResDTO;

import java.sql.SQLException;
import java.util.Scanner;


public class AdminMainContImpl {
    private final AdminCont adminCont;
    // validcheck나중에 필요 함.
    private static Scanner sc = new Scanner(System.in);

    public AdminMainContImpl(AdminCont adminCont) { // 생성될 때 총관리자 컨트롤러 주입
        this.adminCont = adminCont;
    }


    // 총관리자 메뉴 시작
    public void startAdminMenu() throws SQLException {

        while (true) {
            printMenu();
            String input = sc.nextLine().trim();
            while(true) {
                switch (input) {
                    case "1" -> createUser();
                    case "2" -> findUser();
                    case "3" -> updateUser();
                    case "4" -> deleteUser();
                    case "0" -> {
                        System.out.println("프로그램을 종료합니다.");
                        System.exit(0);
                    }
                    default -> System.out.println("유효하지 않은 입력입니다. 재입력하세요.");
                }
            }
        }
    }

    /**
     * 일반 회원의 메뉴를 시작하는 메서드
     * @param loginedUser 로그인 후 반환된 LoginResDTO
     * @throws SQLException
     */
    public void startClientMenu(LoginResDTO loginedUser) throws SQLException {
        printClientMenu();
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> adminCont.readMyAccount(loginedUser);
            case 2 -> adminCont.updateMyAccount(loginedUser);
            case 3 -> adminCont.deleteMyAccount(loginedUser);
            case 4 -> System.out.println("not yet");
            default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
        }

    }

    private void printMenu() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("   회원 관리 시스템   ");
        System.out.println("-".repeat(30));
        System.out.println(" 1. 회원 생성");
        System.out.println(" 2. 회원 조회");
        System.out.println(" 3. 회원 수정");
        System.out.println(" 4. 회원 삭제");
        System.out.println(" 0. 종료");
        System.out.println("-".repeat(30));
    }

    private void printClientMenu() {
        System.out.println("======== 사용자 메뉴 ========");
        System.out.println("1. 내 정보 조회");
        System.out.println("2. 내 정보 수정");
        System.out.println("3. 회원 탈퇴");
        System.out.println("4. 로그아웃");
        System.out.println("============================");
        System.out.print("원하는 메뉴의 번호를 입력하세요: ");
    }

    private void createUser() {
        adminCont.createUser();
    }

    private void findUser() {
        adminCont.findUser();
    }

    private void updateUser() throws SQLException {
        adminCont.updateUser();
    }

    private void deleteUser() {
        adminCont.deleteUser();
    }

}
