package login.repository;

import login.dto.LoginReqDTO;
import login.dto.LoginResDTO;

public interface LoginRepo {
    // 로그인리퀘 디티오 받고, 디비가서 매칭되는 레코드 있는지 확인하기
    LoginResDTO authLogin(String loginId, String password);

}
