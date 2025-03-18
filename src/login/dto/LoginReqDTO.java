package login.dto;

import lombok.Data;

@Data // data 해도 되나?
public class LoginReqDTO {
    // 로그인에 필요한 사용자 입력값 가져오기
    // 아이디, 비밀번호 받기
    String loginId;
    String password;


    public LoginReqDTO(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
