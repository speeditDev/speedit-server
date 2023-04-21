package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    @NotNull
    private String nickname;

    @NotNull
    private String password;

}
