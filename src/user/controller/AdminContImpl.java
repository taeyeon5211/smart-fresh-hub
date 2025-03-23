package user.controller;
import auth.Auth;
import login.controller.LoginCont;
import login.dto.LoginResDTO;
import user.dto.UserDTO;
import user.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminContImpl implements AdminCont {

    private final UserService userService;
    private final LoginCont loginCont;

    private static final Scanner sc = new Scanner(System.in);
    public AdminContImpl(UserService userService, LoginCont loginCont) {
        this.userService = userService;
        this.loginCont = loginCont;
    }


    @Override
    public void createUser() {
        UserDTO newUser = UserInputHelper.getDtoFromUserInfo(); // 사용자 입력값을 받아서,
        userService.createUser(newUser); // 서비스계층에게 넘기기
        // 성공 메시지 추가하기
    }

    /**
     * 회원 한명의 로그인 아이디 입력 받은 후, 회원 정보를 출력하는 메서드
     */
    @Override
    public void findUser() {
        String userLoginId = UserInputHelper.inputUserLoginId();
        try {
            userService.findUser(userLoginId);
            System.out.println(userLoginId + "의 정보 출력하였습니다. 회원관리 메뉴로 돌아갑니다.");
        } catch (Exception e) {
            System.out.println("해당 사용자 아이디를 찾을 수 없습니다. 회원관리 돌아갑니다. ");

        }
        startAdminMenu();
    }

    @Override
    public void fetchAllUsers() {
        // 서비스를 호출하여 디비의 회원 객체 불러오기
        try {
            List<UserDTO> users = userService.fetchAllUsers();
            for(UserDTO user : users) {
                System.out.println(user);
            }
            System.out.println("모든 회원 정보를 출력 완료하였습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        startAdminMenu(); // 메인 메뉴로 뒤돌아가기
    }

    @Override
    public void readMyAccount(LoginResDTO loginedUser) { //
        System.out.println(loginedUser);
    }

    @Override
    public void updateMyAccount(LoginResDTO loginedUser) {
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
        sc.nextLine(); // 남아있는 개행 문자 처리


        UserDTO fetchedUser = userService.findUser(loginedUser.getUserLoginId());
        userService.updateUser(fetchedUser, choice);
    }
    @Override
    public void deleteMyAccount(LoginResDTO loginedUser) {
        userService.deleteUser(loginedUser.getUserLoginId()); // 서비스계층에게 넘기기

    }

    public void printAdminUpdateMenu() {
        System.out.println("===== 회원 정보 수정 메뉴 =====");
        System.out.println("1. 비밀번호 변경");
        System.out.println("2. 주소 변경");
        System.out.println("3. 이름 변경");
        System.out.println("4. 이메일 변경");
        System.out.println("5. 전화번호 변경");
        System.out.println("6. 생년월일 변경");
        System.out.println("7. 사용자 유형 변경");
        System.out.print("변경할 항목의 번호를 선택하세요: ");
    }

    /**
     * admin이 특정 회원의 정보를 수정하는 메서드
     */
    @Override
    public void updateUser(){
        String userLoginId = UserInputHelper.inputUserLoginId();

       printAdminUpdateMenu(); // 수정할 서브메뉴 프린트

        int choice = sc.nextInt(); // 선택값 받기
        sc.nextLine(); // 남아있는 개행문자 처리

        try {
            UserDTO fetchedUser = userService.findUser(userLoginId);
            userService.updateUser(fetchedUser, choice); //
            System.out.println("회원 정보 수정 완료하였습니다.");
            System.out.println(fetchedUser); // 수정된 정보 보여주기
        } catch (Exception e) {
            System.out.println("오류 입니다. 회원 관리 메뉴로 돌아갑니다.");
            throw new RuntimeException(e);
        }
        startAdminMenu(); // 다시 뒤로 돌아가기
    }

    @Override
    public void deleteUser(){

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
    public void readClientBackUpTbl(){

        try {
            userService.readClientBackUpTbl();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        startAdminMenu();
    }


    private void printMenu() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("   회원 관리 시스템   ");
        System.out.println("-".repeat(30));
        System.out.println(" 1. 회원 생성");
        System.out.println(" 2. 회원 조회");
        System.out.println(" 3. 회원 수정");
        System.out.println(" 4. 회원 삭제");
        System.out.println(" 5. 삭제된 모든 회원 조회");
        System.out.println(" 6. 모든 회원 조회");
        System.out.println(" 0. 종료");
        System.out.println("-".repeat(30));
    }
    public void startAdminMenu() {
            printMenu();
            String input = sc.nextLine().trim(); // 루프내에서 계속 입력 받기
            switch (input) {
                case "1" -> createUser();
                case "2" -> findUser();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "5" -> readClientBackUpTbl();
                case "6" -> fetchAllUsers();
                case "0" -> {
                    System.out.println("회원관리를 종료합니다.");
                }
                default -> System.out.println("유효하지 않은 입력입니다. 재입력하세요.");
            }
    }

    /**
     * 일반 회원의 메뉴를 시작하는 메서드
     * @param loginedUser 로그인 후 반환된 LoginResDTO
     */
    public void startClientMenu(){
        Auth auth = Auth.getinstance();
        printClientMenu();
        int choice = sc.nextInt();


        switch (choice) {
            case 1 -> readMyAccount(auth.getLoginResDto());
            case 2 -> updateMyAccount(auth.getLoginResDto());
            case 3 -> deleteMyAccount(auth.getLoginResDto());
            case 4 -> {
                System.out.println("로그아웃 합니다. 메인 메뉴로 돌아갑니다.");
                loginCont.startLoginPage();
            }
            default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }

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
}


