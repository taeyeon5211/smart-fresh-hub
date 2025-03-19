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


          printMenu();
            // if user... 추가하기
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createUser();
                case "2" -> findUser();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "0" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("유효하지 않은 입력입니다. 재입력하세요.");
            }
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
