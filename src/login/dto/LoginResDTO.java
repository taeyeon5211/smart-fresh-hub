package login.dto;

import lombok.*;
import user.vo.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResDTO {
    // 회원 정보가 디비에 있을 경우 LoginResDTO에 담아 반환한다.
    // 이 디티오는 회원 정보가 담겨 있음

    private Integer userId;
    private String userLoginId;
    private String userPassword;
    private String userAddress;
    private String userName;
    private String userEmail;
    private String userPhone;
    private LocalDate userBirthDate; // LocalDate 이유
    private LocalDateTime userCreatedAt; // localDateTime 이유
    private String userType;

    public LoginResDTO(String userLoginId, String userPassword, String userAddress, String userName,
                   String userEmail, String userPhone, LocalDate userBirthDate, String userType) {
        this.userLoginId = userLoginId;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userBirthDate = userBirthDate;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return String.format(
                """
                ==============================
                회원 정보
                ------------------------------
                로그인 ID    : %s
                주소         : %s
                이름         : %s
                이메일       : %s
                전화번호     : %s
                생년월일     : %s
                가입일       : %s
                사용자 유형  : %s
                ==============================
                """,
                 userLoginId, userAddress, userName,
                userEmail, userPhone, userBirthDate, userCreatedAt, userType
        );
    }

}
