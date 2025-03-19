package login.repository;
import login.dto.LoginReqDTO;
import login.dto.LoginResDTO;
import object.ObjectIo;
import user.vo.UserType;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LoginRepoImpl implements LoginRepo {
    Connection connection = ObjectIo.getConnection();
    CallableStatement cs = null;
    ResultSet rs = null;

    @Override
    public LoginResDTO authLogin(String loginId, String password) {
        String query = "{CALL CheckUserExists(?,?)}";

        try {
            cs = connection.prepareCall(query);
            cs.setString(1, loginId);
            cs.setString(2, password);
            cs.execute();

            rs = cs.getResultSet(); // 결과 집합 가져오기

            if (rs != null && rs.next()) { // 결과가 존재하는 경우
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LoginResDTO loginResDto = LoginResDTO.builder()
                        .userLoginId(rs.getString("user_login_id"))
                        .userName(rs.getString("user_name"))
                        .userPassword(rs.getString("user_password"))
                        .userAddress(rs.getString("user_address"))
                        .userEmail(rs.getString("user_email"))
                        .userPhone(rs.getString("user_phone"))
                        .userBirthDate(rs.getDate("user_birth_date").toLocalDate())
                        .userType((rs.getString("user_type").toUpperCase()))
                        .userCreatedAt(LocalDateTime.parse(rs.getString("user_created_at"), formatter))
                        .build();

                return loginResDto;
            } else {
                System.out.println("사용자 아이디 " + loginId + "를 찾지 못했습니다. 로그인이나 비밀번호가 틀렸습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 자원 해제
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
