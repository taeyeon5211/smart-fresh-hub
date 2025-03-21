package user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BackupDto {
    private String userLoginId;
    private String userName;
    private String userEmail;
    private String createdAt;
    private String deletedAt;
}
