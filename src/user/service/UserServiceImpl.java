package user.service;

import user.controller.AdminCont;
import user.controller.UserInputHelper;
import user.dto.BackupDto;
import user.dto.UserDTO;
import user.repository.UserRepo;
import user.repository.UserRepoImpl;
import user.vo.UserVO;
import user.vo.UserType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    // repo
    private final UserRepo userRepo;


    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    // 생성자 이용 dto변환
    public static UserDTO convertToUserDTO(
            String loginId, String password, String address, String name,
            String email, String phone, LocalDate birthDate, UserType userType) {

        return new UserDTO(loginId, password, address, name, email, phone, birthDate, userType);
    }

    @Override
    public void createUser(UserDTO dto) {
        // 1. 디비에서 중복회원인지 검사

        // dto 를 vo로 변환후 repo불러 디비에 저장
        UserVO userVo = new UserVO(dto); // vo로 변환
        userRepo.insertUser(userVo); // repo를 불러 vo를 디비에 저장.
        System.out.println("회원 아이디 " + userVo.getUserLoginId()+ " 를 생성하였습니다. ");
    }

    public void createNewAccount() {
      UserDTO newUser = UserInputHelper.getDtoFromUserInfo();
      UserVO userVo = new UserVO(newUser);
        userRepo.insertUser(userVo); // repo를 불러 vo를 디비에 저장.
        System.out.println("회원 아이디 " + userVo.getUserLoginId()+ " 를 생성하였습니다. ");
    }

    @Override
    public Boolean deleteUser(String userLoginId) {
        UserVO foundUser = userRepo.findUser(userLoginId);
        if (foundUser != null) {
            // 디비에 로그인 아이디가 있으면
            // 삭제하기
            userRepo.deleteUser(foundUser);
            System.out.println("회원 아이디 " + userLoginId + " 를 시스템에서 삭제했습니다.");
            return true;
        } else {
            throw new IllegalArgumentException("해당회원이 존재하지 않습니다.");
        }

    }



    /**
     *
     * @param
     */
    public void updateUser(UserDTO updatedUser, int choice){
        UserVO userVo = new UserVO(updatedUser);

            switch (choice) {
                case 1 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserPassword());
                case 2 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserAddress());
                case 3 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserName());
                case 4 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserEmail());
                case 5 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserPhone());
                case 6 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserBirthDate().toString());
                case 7 -> userRepo.updateUser(userVo, choice, UserInputHelper.inputUserType().toString());
                default -> System.out.println("잘못된 입력입니다: " + choice);
            }

    }

    /**
     * 찾은 회원을 dto로 변환하여 컨트롤러에 전달하는 메소드
     *
     * @param userLoginId
     * @return
     */
    @Override
    public UserDTO findUser(String userLoginId) {
        try {
            UserVO foundVo = userRepo.findUser(userLoginId);
                System.out.println(foundVo.getUserLoginId() + "사용자 정보는 아래와 같습니다. ");
                System.out.println(foundVo);
                return new UserDTO(foundVo);

        } catch (RuntimeException e) {
            //System.out.println("회원 정보가 없습니다.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BackupDto> readClientBackUpTbl() {
        return userRepo.readClientBackUpTbl();
    }

    /**
     * 모든 직원을 조회하는 메소드
     *
     * @return
     */
    @Override
    public List<UserDTO> findAllUsers() {
        List<UserVO> allUsers = userRepo.findAllUsers();

        List<UserDTO> userDTOList = new ArrayList<>();

        for (UserVO vo : allUsers) {
            UserDTO userDto = new UserDTO(vo); // 한개씩 dto 객체로 변환
            userDTOList.add(userDto); // 변환한 dto를 리스트에 저장.
        }
        return userDTOList;
    }

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl(new UserRepoImpl());
        userService.readClientBackUpTbl().forEach(System.out::println);
    }
}
