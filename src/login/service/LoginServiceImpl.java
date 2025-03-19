package login.service;

import login.dto.LoginReqDTO;
import login.dto.LoginResDTO;
import login.repository.LoginRepo;

import java.sql.SQLException;


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
        // 받은 정보를 DTO로 생성
        LoginReqDTO loginReqDTO = new LoginReqDTO(loginId, password);

        try {
            // 레포지토리에서 로그인 정보 확인, resDto 돌려받기
            LoginResDTO loginResDto = loginRepo.authLogin(loginReqDTO);

            if (loginResDto == null) {
                System.out.println("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
                throw new IllegalArgumentException("잘못된 로그인 정보입니다.");
            }
            // resDto가 널이 아니면 성공
            System.out.println("로그인 성공: " + loginResDto.getUserName());
            return loginReqDTO;
        } catch (Exception e) {
            System.out.println("로그인 중 예기치 않은 오류 발생: " + e.getMessage()); // 어떤 오류인지 확인하기
            throw new RuntimeException("로그인 처리 중 오류가 발생했습니다.", e);
        }
    }


}
