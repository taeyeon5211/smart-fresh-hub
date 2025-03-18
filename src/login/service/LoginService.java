package login.service;

import login.dto.LoginReqDTO;

public interface LoginService {
    LoginReqDTO authLogin(String loginId, String password); // 컨트롤러로 받은 정보를 디티오로 만들어서 레포에게 전달 하기
}
