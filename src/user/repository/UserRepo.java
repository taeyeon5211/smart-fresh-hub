package user.repository;

import user.dto.UserDTO;
import user.vo.UserVO;

import java.util.List;

public interface UserRepo {
    UserVO findUser(String loginId); // 디비 조회결과를 vo로 변환하여 반환
    List<UserVO> findAllUsers();
    Boolean insertUser(UserVO userVo); //
    void updateUser(UserDTO userDto);
    Boolean deleteUser(UserVO userVo);

}
