package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordRequest {

    private String nickname;
    private String email;
    private String birth;

}
