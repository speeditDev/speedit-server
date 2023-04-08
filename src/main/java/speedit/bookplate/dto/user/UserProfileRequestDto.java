package speedit.bookplate.dto.user;

import lombok.Getter;

@Getter
public class UserProfileRequestDto {

    private String profileImg;

    private String nickname;

    private String job;

    private String company;

    private boolean isEmailCertified;

    private String introduction;

}
