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
    public LoginResDTO authLogin(String loginId, String password) {
//        // 받은 정보를 디티오로 만들기
//        LoginReqDTO loginReqDTO = new LoginReqDTO(loginId, password);
//
//        // 만든 디티오를 레포로 넘기기
        LoginResDTO loginResDto = loginRepo.authLogin(loginId, password);


        return loginResDto;
    }

}
