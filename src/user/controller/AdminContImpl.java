package user.controller;
import user.dto.UserDTO;
import user.service.UserService;

public class AdminContImpl implements AdminCont {

    private final UserService userService;

    public AdminContImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void createUser() {
        UserDTO newUser = UserInputHelper.getDtoFromUserInfo(); // 사용자 입력값을 받아서,
        userService.createUser(newUser); // 서비스계층에게 넘기기
        // 성공 메시지 추가하기
    }

    @Override
    public void findUser() {
        String userLoginId = UserInputHelper.inputUserLoginId();
        userService.findUser(userLoginId);

    }

    @Override
    public void updateUser() {
        // 수정된 필드값만 디티오에 값을 넣고 보내고 나머지는 널 처리하기
        UserDTO userDto = UserInputHelper.getDtoFromUserInfo();
        userService.updateUser(userDto);

    }

    @Override
    public void deleteUser() {
        String userLoginId = UserInputHelper.inputUserLoginId();
        userService.deleteUser(userLoginId); // 서비스계층에게 넘기기
    }
}
