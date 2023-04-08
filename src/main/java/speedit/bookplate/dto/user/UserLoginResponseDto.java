package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserLoginResponseDto {

    private String accessToken;
    private String refreshToken;

}
