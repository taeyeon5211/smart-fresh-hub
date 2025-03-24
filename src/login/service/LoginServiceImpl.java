package login.service;
import login.dto.LoginResDTO;
import login.repository.LoginRepo;


public class LoginServiceImpl implements LoginService {
    private final LoginRepo loginRepo;

    public LoginServiceImpl(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }


    @Override
    public LoginResDTO authLogin(String loginId, String password) {
        LoginResDTO loginResDto = loginRepo.authLogin(loginId, password);
        return loginResDto;
    }

}
