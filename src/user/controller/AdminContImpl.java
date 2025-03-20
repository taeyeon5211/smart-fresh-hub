package user.controller;
import login.dto.LoginResDTO;
import user.dto.UserDTO;
import user.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminContImpl implements AdminCont {

    private final UserService userService;
    private static final Scanner sc = new Scanner(System.in);
    public AdminContImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void createUser() {
        UserDTO newUser = UserInputHelper.getDtoFromUserInfo(); // 사용자 입력값을 받아서,
        userService.createUser(newUser); // 서비스계층에게 넘기기
        // 성공 메시지 추가하기
    }

    @Override
    public void findUser() {
        String userLoginId = UserInputHelper.inputUserLoginId();
        userService.findUser(userLoginId);

    }
    @Override
    public void readMyAccount(LoginResDTO loginedUser) { //
        System.out.println(loginedUser);
    }

    @Override
    public void updateMyAccount(LoginResDTO loginedUser) throws SQLException {
        System.out.println("===== 회원 정보 수정 메뉴 =====");
        System.out.println("1. 비밀번호 변경");
        System.out.println("2. 주소 변경");
        System.out.println("3. 이름 변경");
        System.out.println("4. 이메일 변경");
        System.out.println("5. 전화번호 변경");
        System.out.println("6. 생년월일 변경");
        System.out.println("7. 사용자 유형 변경");
        System.out.print("변경할 항목의 번호를 선택하세요: ");

        int choice = sc.nextInt();


        UserDTO fetchedUser = userService.findUser(loginedUser.getUserLoginId());
        userService.updateUser(fetchedUser, choice);
    }
    @Override
    public void deleteMyAccount(LoginResDTO loginedUser) {
        userService.deleteUser(loginedUser.getUserLoginId()); // 서비스계층에게 넘기기

    }

    @Override
    public void updateUser() throws SQLException {
        String userLoginId = UserInputHelper.inputUserLoginId();

        System.out.println("===== 회원 정보 수정 메뉴 =====");
        System.out.println("1. 비밀번호 변경");
        System.out.println("2. 주소 변경");
        System.out.println("3. 이름 변경");
        System.out.println("4. 이메일 변경");
        System.out.println("5. 전화번호 변경");
        System.out.println("6. 생년월일 변경");
        System.out.println("7. 사용자 유형 변경");
        System.out.print("변경할 항목의 번호를 선택하세요: ");

        int choice = sc.nextInt();


        UserDTO fetchedUser = userService.findUser(userLoginId);
        userService.updateUser(fetchedUser, choice);

    }

    @Override
    public void deleteUser() throws SQLException {

            System.out.println("삭제할 회원의 아이디를 입력하세요: ");
            String userLoginId = sc.nextLine().trim();
        try {
            userService.deleteUser(userLoginId);
            System.out.println("회원 삭제가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("삭제할 회원이 존재하지 않습니다. 메인 메뉴로 돌아갑니다.");
        }
        startAdminMenu();
    }

    @Override
    public void readClientBackUpTbl() throws SQLException {
        userService.readClientBackUpTbl();
    }


    private void printMenu() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("   회원 관리 시스템   ");
        System.out.println("-".repeat(30));
        System.out.println(" 1. 회원 생성");
        System.out.println(" 2. 회원 조회");
        System.out.println(" 3. 회원 수정");
        System.out.println(" 4. 회원 삭제");
        System.out.println(" 5. 샥제된 회원 모두 조회");
        System.out.println(" 0. 종료");
        System.out.println("-".repeat(30));
    }
    public void startAdminMenu() throws SQLException {


        printMenu();

        while(true) {
            String input = sc.nextLine().trim(); // 루프내에서 계속 입력 받기
            switch (input) {
                case "1" -> createUser();
                case "2" -> findUser();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "5" -> readClientBackUpTbl();
                case "0" -> {
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }
                default -> System.out.println("유효하지 않은 입력입니다. 재입력하세요.");
            }
        }

    }

}
