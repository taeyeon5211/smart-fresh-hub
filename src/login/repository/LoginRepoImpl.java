package login.repository;
import login.dto.LoginReqDTO;
import login.dto.LoginResDTO;
import object.ObjectIo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginRepoImpl implements LoginRepo {
    Connection connection = ObjectIo.getConnection();
    CallableStatement cs = null;
    ResultSet rs = null;

    @Override
    public LoginResDTO authLogin(LoginReqDTO loginReqDTO) throws SQLException {
        // 디비에서 찾아본다 FindUser이용해서 아이디 매칭하는 회원이 있는지 알아본다
        String query = "{CALL CheckUserExists(?,?,? )}";


        try {
            cs = connection.prepareCall(query);
            cs.setString(1, loginReqDTO.getLoginId());
            cs.setString(2, loginReqDTO.getPassword());
            // OUT 파라미터 등록
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute(); // 반환 - true 면 resultSet 존재 -> getResultSet() 사용가능

            int loginStatus = cs.getInt(3);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // rs 돌면서 LoginResDTO 생성

            if (loginStatus == 1) {
                rs = cs.getResultSet(); // 존재 하는 회원의 튜플
                LoginResDTO loginResDto = null;
                while (rs.next()) {
                    loginResDto = new LoginResDTO().builder()
                            .userLoginId(rs.getString("user_login_id"))
                            .userName(rs.getString("user_name"))
                            .userPassword(rs.getString("user_password"))
                            .userAddress(rs.getString("user_address"))
                            .userEmail(rs.getString("user_email"))
                            .userPhone(rs.getString("user_phone"))
                            .userBirthDate(rs.getDate("user_birth_date")
                                    .toLocalDate()).userCreatedAt(LocalDateTime.parse(rs.getString("user_created_at"), formatter))
                            .build();

                }
                // 유저가 있다면 유저 반환
                return loginResDto;
            }

        } catch (SQLException | IllegalArgumentException e) {
            throw new RuntimeException();
        }
        return null;
    }
}

