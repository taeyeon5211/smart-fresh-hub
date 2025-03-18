package user.service;

import user.dto.UserDTO;
import user.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
   void createUser(UserDTO userDto);
   Boolean deleteUser(String userLoginId); //
   void updateUser(UserDTO userDTO);
   UserDTO findUser(String userLoginId);
   List<UserDTO> findAllUsers();
}
