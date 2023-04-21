package speedit.bookplate.dto.user;

import lombok.Getter;
import speedit.bookplate.domain.User;

@Getter
public class UserRequest {

    private String profileImg;
    private String nickname;
    private String job;
    private String company;
    private boolean isEmailCertified;
    private String introduction;

    public User toUser(){
        return User.builder()
                .nickname(nickname)
                .profileImg(profileImg)
                .job(job)
                .company(company)
                .isEmailCertified(isEmailCertified)
                .introduction(introduction)
                .build();
    }

}
