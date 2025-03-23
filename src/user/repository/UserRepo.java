package user.repository;

import user.dto.UserDTO;
import user.dto.BackupDto;
import user.vo.UserVO;
import java.util.List;

public interface UserRepo {
    UserVO findUser(String loginId); // 디비 조회결과를 vo로 변환하여 반환
    List<UserDTO> fetchAllUsers();
    Boolean insertUser(UserVO userVo); //
    Boolean deleteUser(UserVO userVo);
    void updateUser(UserVO userVo, int choice, String newValue);

    List<BackupDto> readClientBackUpTbl();
}
