package login.service;

import login.dto.LoginReqDTO;
import login.dto.LoginResDTO;
import login.repository.LoginRepo;


public class LoginServiceImpl implements LoginService {
    private final LoginRepo loginRepo;

    public LoginServiceImpl(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }


    @Override
    public LoginReqDTO authLogin(String loginId, String password) {
        // 받은 정보를 디티오로 만들기
        LoginReqDTO loginReqDTO = new LoginReqDTO(loginId, password);

        // 만든 디티오를 레포로 넘기기
       LoginResDTO loginResDto = loginRepo.authLogin(loginReqDTO);
       if (loginResDto != null) {
           System.out.println("로그인 성공했습니다.");
       } else {
           System.out.println("로그인 실패했습니다.");
       }
        return loginReqDTO;
    }
}
