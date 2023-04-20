package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UserLoginRequestDto {

    @NotNull
    private String nickname;

    @NotNull
    private String password;

}
