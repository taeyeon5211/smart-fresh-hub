package user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.vo.UserType;
import user.vo.UserVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer userId;
    private String userLoginId;
    private String userPassword;
    private String userAddress;
    private String userName;
    private String userEmail;
    private String userPhone;
    private LocalDate userBirthDate; // LocalDate 이유
    private LocalDateTime userCreatedAt; // localDateTime 이유
    private UserType userType;


    public UserDTO(String userLoginId, String userPassword, String userAddress, String userName,
                   String userEmail, String userPhone, LocalDate userBirthDate, UserType userType) {
        this.userLoginId = userLoginId;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userBirthDate = userBirthDate;
        this.userType = userType;
    }


    /**
     * vo객체를 받아 dto 로 변환하는 메소드
     * @param vo
     */
    public UserDTO(UserVO vo) {
        this.userId = vo.getUserId();
        this.userName = vo.getUserName();
        this.userLoginId = vo.getUserLoginId();
        this.userPassword = vo.getUserPassword();
        this.userAddress = vo.getUserAddress();
        this.userEmail = vo.getUserEmail();
        this.userPhone = vo.getUserPhone();
        this.userBirthDate = vo.getUserBirthDate();
        this.userCreatedAt = vo.getUserCreatedAt();
        this.userType = vo.getUserType();
    }

    @Override
    public String toString() {
        return String.format(
                """
                ==================================================================================================================================
                userId   | 로그인 ID  | 이름    | 비밀번호   | 주소        | 이메일               | 전화번호       | 생년월일    | 가입일               | 사용자 타입
                ----------------------------------------------------------------------------------------------------------------------------------
                %-8d | %-10s | %-6s | %-10s | %-10s | %-20s | %-13s | %-10s | %-19s | %-10s
                ==================================================================================================================================
                """,
                userId, userLoginId, userName, userPassword, userAddress, userEmail, userPhone, userBirthDate, userCreatedAt, userType
        );
    }


}
