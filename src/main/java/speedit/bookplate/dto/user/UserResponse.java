package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

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
    private boolean following;

    public static UserResponse of(final User user, final boolean following){
        return UserResponse.builder()
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .birth(user.getBirth())
                .gender(user.getGender().getGender())
                .job(user.getJob().getTitle())
                .company(user.getCompany())
                .companyEmail(user.getCompanyEmail())
                .isEmailCertified(user.getIsEmailCertified())
                .introduction(user.getIntroduction())
                .followerCount(user.getFollowerCount())
                .following(following)
                .build();
    }

}
