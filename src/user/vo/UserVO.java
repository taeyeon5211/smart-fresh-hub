package user.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import user.dto.UserDTO;
import user.vo.UserType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
public class UserVO {

    private final Integer userId;
    private final String userLoginId;
    private final String userPassword;
    private final String userAddress;
    private final String userName;
    private final String userEmail;
    private final String userPhone;
    private final LocalDate userBirthDate; // LocalDate 이유
    private final LocalDateTime userCreatedAt; // localDateTime 이유
    private final UserType userType;

    public UserVO(Integer userId, String userLoginId, String userPassword, String userAddress, String userName, String userEmail, String userPhone, LocalDate userBirthDate, LocalDateTime userCreatedAt, UserType userType) {
        this.userId = userId;
        this.userLoginId = userLoginId;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userBirthDate = userBirthDate;
        this.userCreatedAt = userCreatedAt;
        this.userType = userType;
    }



    // dto 를 전달받아 vo 를 dto로 변환
    public UserVO(UserDTO dto) {
        this.userId = dto.getUserId();
        this.userName = dto.getUserName();
        this.userLoginId = dto.getUserLoginId();
        this.userPassword = dto.getUserPassword();
        this.userAddress = dto.getUserAddress();
        this.userEmail = dto.getUserEmail();
        this.userPhone = dto.getUserPhone();
        this.userBirthDate = dto.getUserBirthDate();
        this.userCreatedAt = dto.getUserCreatedAt();
        this.userType = (dto.getUserType());
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return userId == userVO.userId && Objects.equals(userLoginId, userVO.userLoginId) && Objects.equals(userPassword, userVO.userPassword) && Objects.equals(userAddress, userVO.userAddress) && Objects.equals(userName, userVO.userName) && Objects.equals(userEmail, userVO.userEmail) && Objects.equals(userPhone, userVO.userPhone) && Objects.equals(userBirthDate, userVO.userBirthDate) && Objects.equals(userCreatedAt, userVO.userCreatedAt) && userType == userVO.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userLoginId, userPassword, userAddress, userName, userEmail, userPhone, userBirthDate, userCreatedAt, userType);
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
                ==============================
                """,
                userLoginId, userAddress, userName,
                userEmail, userPhone, userBirthDate, userCreatedAt
        );
    }


}
