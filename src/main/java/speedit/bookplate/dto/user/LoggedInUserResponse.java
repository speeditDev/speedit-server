package speedit.bookplate.dto.user;

import lombok.*;
import speedit.bookplate.domain.User;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggedInUserResponse {

    private String nickname;
    private String profileImg;
    private String birth;
    private String gender;
    private String job;
    private String company;
    private String companyEmail;
    private boolean isEmailCertified;
    private String introduction;
    private int followerCount;
    private int followingCount;

    public static LoggedInUserResponse from(final User user,final int followingCount){
        return LoggedInUserResponse.builder()
                .profileImg(user.getProfileImg())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .gender(user.getGender().getGender())
                .job(user.getJob())
                .company(user.getCompany())
                .companyEmail(user.getCompanyEmail())
                .isEmailCertified(false)
                .introduction(user.getIntroduction())
                .followerCount(user.getFollowerCount())
                .followingCount(followingCount)
                .build();
    }
}
