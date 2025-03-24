package user.service;

import user.dto.BackupDto;
import user.dto.UserDTO;
import user.vo.UserVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserService {
   void createUser(UserDTO userDto);
   Boolean deleteUser(String userLoginId); //
   void updateUser(UserDTO userDTO, int choice);
   UserDTO findUser(String userLoginId);

   /**
    * 데이터베이스의 모든 회원 객체를 반환하는 메서드
    * @return
    */
   List<UserDTO> fetchAllUsers();
    void createNewAccount();
   List<BackupDto> readClientBackUpTbl();
}
