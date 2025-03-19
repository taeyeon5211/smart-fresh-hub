package login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import user.vo.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    public LoginResDTO(String userLoginId, String userPassword) {
        this.userLoginId = userLoginId;
        this.userPassword = userPassword;
    }

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


}
