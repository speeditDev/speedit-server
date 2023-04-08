package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequestDto {

    private String nickname;
    private String password;

}
