package login.service;

import login.dto.LoginReqDTO;
import login.dto.LoginResDTO;
import login.repository.LoginRepo;


public class LoginServiceImpl implements LoginService {
    private final LoginRepo loginRepo;

    public LoginServiceImpl(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }


    /**
     * 사용자 정보를 입력받아 dto 객체로 만들고, 객체를 repo로 넘기는 메서드
     * @param loginId
     * @param password
     * @return LoginReqDTO
     */
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
