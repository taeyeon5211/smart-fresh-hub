package user.controller;
import user.dto.UserDTO;
import user.vo.UserType;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * 회원 가입시 사용자 입력 필드를 받는 헬퍼 클래스
 */
public class UserInputHelper {
    private static final Scanner scanner = new Scanner(System.in);


    public static UserDTO getDtoFromUserInfo() {

        System.out.println("생성할 사용자 정보를 입력하세요.");
        UserDTO user = new UserDTO();
        user.setUserLoginId(inputUserLoginId());
        user.setUserPassword(inputUserPassword());
        user.setUserAddress(inputUserAddress());
        user.setUserName(inputUserName());
        user.setUserEmail(inputUserEmail());
        user.setUserPhone(inputUserPhone());
        user.setUserBirthDate(inputUserBirthDate());
        user.setUserType(inputUserType());

        return user;
    }


    public static String inputUserLoginId() {
        System.out.print("로그인 ID를 입력하세요: ");

        Scanner scanner = new Scanner(System.in);
        String userLoginId;

        while (true) {
            System.out.print("로그인 ID를 입력하세요: ");
            userLoginId = scanner.next().trim();

            // 1. 빈 문자열 또는 공백 검사
            if (userLoginId.isEmpty()) {
                System.out.println("로그인 ID는 공백일 수 없습니다. 다시 입력하세요.");
                continue;
            }

            // 2. 길이 제한 (5~20자)
            if (userLoginId.length() < 5 || userLoginId.length() > 20) {
                System.out.println("로그인 ID는 5~20자 사이여야 합니다. 다시 입력하세요.");
                continue;
            }

            // 3. 허용된 문자만 사용 (영문, 숫자, '_', '-')
            if (!userLoginId.matches("^[a-zA-Z0-9_-]+$")) {
                System.out.println("로그인 ID는 영문, 숫자, '_', '-'만 사용할 수 있습니다. 다시 입력하세요.");
                continue;
            }

            // 유효성 검사 통과 시 반환
            break;
        }
        return userLoginId;
    }

    public static String inputUserPassword() {
        System.out.print("비밀번호를 입력하세요: ");

        String userPassword;

        while (true) {
            System.out.print("비밀번호를 입력하세요: ");
            userPassword = scanner.next().trim();

            // 1. 길이 제한 (8~20자)
            if (userPassword.length() < 8 || userPassword.length() > 20) {
                System.out.println("비밀번호는 8~20자 사이여야 합니다. 다시 입력하세요.");
                continue;
            }

            // 2. 대문자, 소문자, 숫자, 특수문자 포함 여부 검사
            if (!userPassword.matches(".*[A-Z].*")) {
                System.out.println("비밀번호에는 최소 1개의 대문자가 포함되어야 합니다. 다시 입력하세요.");
                continue;
            }
            if (!userPassword.matches(".*[a-z].*")) {
                System.out.println("비밀번호에는 최소 1개의 소문자가 포함되어야 합니다. 다시 입력하세요.");
                continue;
            }
            if (!userPassword.matches(".*[0-9].*")) {
                System.out.println("비밀번호에는 최소 1개의 숫자가 포함되어야 합니다. 다시 입력하세요.");
                continue;
            }
            if (!userPassword.matches(".*[!@#$%^&*()].*")) {
                System.out.println("비밀번호에는 최소 1개의 특수문자(!@#$%^&*())가 포함되어야 합니다. 다시 입력하세요.");
                continue;
            }

            // 3. 공백 포함 여부 검사
            if (userPassword.contains(" ")) {
                System.out.println("비밀번호에는 공백이 포함될 수 없습니다. 다시 입력하세요.");
                continue;
            }

            // 모든 조건을 만족하면 반환
            break;
        }

        return userPassword;

    }

    public static String inputUserAddress() {
        System.out.print("주소를 입력하세요: ");
        scanner.nextLine(); // 버퍼 비우기
        return scanner.nextLine();
    }

    public static String inputUserName() {
        System.out.print("이름을 입력하세요: ");
        return scanner.next();
    }

    public static String inputUserEmail() {
        System.out.print("이메일을 입력하세요: ");
        return scanner.next();
    }

    public static String inputUserPhone() {
        System.out.print("휴대폰 번호를 입력하세요 (예: 010-1234-5678): ");
        return scanner.next();
    }

    public static LocalDate inputUserBirthDate() {
        System.out.print("생년월일을 입력하세요 (YYYY-MM-DD): ");
        while (true) {
            try {
                return LocalDate.parse(scanner.next());
            } catch (DateTimeParseException e) {
                System.out.print("올바른 날짜 형식(YYYY-MM-DD)으로 입력하세요: ");
            }
        }
    }

    public static UserType inputUserType() {
        System.out.print("사용자 유형을 입력하세요 (admin/client): ");
        while (true) {
            String input = scanner.next().toLowerCase();
            if (input.equals("admin")) return UserType.ADMIN;
            if (input.equals("client")) return UserType.CLIENT;
            System.out.print("올바른 값을 입력하세요 (admin/client): ");
        }
    }
}