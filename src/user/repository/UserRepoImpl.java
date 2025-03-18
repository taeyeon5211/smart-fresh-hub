package user.repository;

import object.ObjectIo;
import user.dto.UserDTO;
import user.dto.UserDTO;
import user.vo.UserVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * 1. 회원 정보를 저장, 조회, 수정, 삭제하는 repository 클래스
 * 2. 디비에서 가져온 데이터를 다른 계층이 필요한 dto 형태로 가공하여 반환
 */
public class UserRepoImpl implements UserRepo {

    Connection connection = ObjectIo.getConnection();
    CallableStatement cs = null;
    ResultSet rs = null;

    /**
     * 회원을 디비에 생성하는 메서드
     *
     * @param vo
     * @return Boolean
     */
    @Override
    public Boolean insertUser(UserVO vo) {
        Boolean isSuccess = false;
        // user_id, created_at  등은 사용자로부터 받는 것 아님!

        String InsertUserPrc = "CALL InsertUser(?,?,?,?,?,?, ?,?)";
        try { // insertQuery 실행시켜 디비에 회원 레코드 저장하기
            cs = connection.prepareCall(InsertUserPrc);
            cs.setString(1, vo.getUserLoginId());
            cs.setString(2, vo.getUserName());
            cs.setString(3, vo.getUserPassword());
            cs.setString(4, vo.getUserAddress());
            cs.setString(5, vo.getUserEmail());
            cs.setString(6, vo.getUserPhone());
            cs.setString(7, String.valueOf(vo.getUserBirthDate()));
            cs.setString(8, String.valueOf(vo.getUserType()));
            cs.execute();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }

        return isSuccess = true; // 성공적으로 생성되면 result
    }


    /**
     * 로그인 아이디와 일치하는 회원을 찾아 반환한다.
     *
     * @param loginId
     * @return 디비에서 조회된 회원 dto
     * 디비에서 조회한 회원 정보를 dto 객체로 변환하여 반환
     */
    @Override
    public UserVO findUser(String loginId) {

        String findPrc = "{CALL FindUser(?)}";

        try {
            cs = connection.prepareCall(findPrc);
            cs.setString(1, loginId);
            rs = cs.executeQuery();

            UserVO userVo = null; // 디비에서 찾은 회원 vo 로 변환

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (rs.next()) {
                userVo = UserVO.builder() // rs에서 받은 레코드로 vo 생성하기
                        .userLoginId(rs.getString("user_login_id"))
                        .userName(rs.getString("user_name"))
                        .userPassword(rs.getString("user_password"))
                        .userAddress(rs.getString("user_address"))
                        .userEmail(rs.getString("user_email"))
                        .userPhone(rs.getString("user_phone"))
                        .userBirthDate(rs.getDate("user_birth_date")
                                .toLocalDate()).userCreatedAt(LocalDateTime.parse(rs.getString("user_created_at"), formatter))
                        .build();

                cs.close();
            }
            return userVo; //
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserVO> findAllUsers() {
        // 모든 회원을 조회하는 프로시저 부르기
        return List.of();
    }

    /**
     * 전달받은 회원 dto 객체를 디비에 저장한다
     *
     * @param
     * @return
     */
    @Override
    public void updateUser(UserDTO user) {
        String sql = "{CALL UpdateUser(?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, user.getUserName());
            cs.setString(2, user.getUserLoginId());
            cs.setString(3, user.getUserPassword());
            cs.setString(4, user.getUserAddress());
            cs.setString(5, user.getUserEmail());
            cs.setString(6, user.getUserPhone());
            cs.setDate(7, user.getUserBirthDate() != null ? Date.valueOf(user.getUserBirthDate()) : null);
            cs.setString(8, String.valueOf(user.getUserType()));

            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 정보 업데이트 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 회원 한명을 디비에서 삭제한다.
     *
     * @param vo
     * @return
     */
    @Override
    public Boolean deleteUser(UserVO vo) {
        Boolean isSuccess = false;

        String deletePrc = "{ CALL DeleteUser(?) }";

        try {
            cs = connection.prepareCall(deletePrc);
            cs.setString(1, vo.getUserLoginId()); // 로그인 아이디 매칭되는 회원 삭제
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 닫아야하나?
            ;
        }
        return isSuccess;
    }
}
