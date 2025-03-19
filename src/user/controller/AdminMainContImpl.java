package user.controller;

import java.sql.SQLException;
import java.util.Scanner;


public class AdminMainContImpl {
    private final AdminCont adminCont;
    // validcheck나중에 필요 함.
    private static Scanner sc = new Scanner(System.in);
    public AdminMainContImpl(AdminCont adminCont) { // 생성될 때 총관리자 컨트롤러 주입
        this.adminCont = adminCont;
    }


    public void start() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printFirstMenu();
            String firstMenu = sc.nextLine();

            try {
                firstMenu = scanner.nextLine().trim(); // 문자열 입력 방지
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                continue;
            }

            switch (firstMenu) {
                case "1":
                    printMenu();
                    startUserManageMenu();

                    break;
                case "2":
                    System.out.println("재무 관리 메뉴로 이동합니다.");
                    break;
                case "3":
                    System.out.println("창고 관리 메뉴로 이동합니다.");
                    break;
                case "4":
                    System.out.println("재고 관리 메뉴로 이동합니다.");
                    break;
                case "5":
                    System.out.println("입고 관리 메뉴로 이동합니다.");
                    break;
                case "6":
                    System.out.println("출고 관리 메뉴로 이동합니다.");
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return; // while 문 탈출
                default:
                    System.out.println("잘못된 선택입니다. 0~6 사이의 숫자를 입력하세요.");
            }

        }
    }

    private void startUserManageMenu() throws SQLException {

        String choice = sc.nextLine().trim();

        while(true) {
            switch (choice) {
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

    private void printFirstMenu() {
        System.out.println("\n===== 메인 메뉴 =====");
        System.out.println("1. 회원 관리");
        System.out.println("2. 재무 관리");
        System.out.println("3. 창고 관리");
        System.out.println("4. 재고 관리");
        System.out.println("5. 입고 관리");
        System.out.println("6. 출고 관리");
        System.out.println("0. 종료");
        System.out.print("메뉴를 선택하세요: ");
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
