package speedit.bookplate.dto.user;

import lombok.*;
import speedit.bookplate.domain.User;
import speedit.bookplate.dto.feed.FeedResponseDto;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {

    private String nickname;

    private String profileImg;

    private String birth;

    private String gender;

    private String job;

    private String company;

    private String companyEmail;

    private boolean isEmailCertified;

    private String introduction;

    public static UserProfileResponse from(final User user){
        return UserProfileResponse.builder()
                .profileImg(user.getProfileImg())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .gender(user.getGender().getGender())
                .job(user.getJob())
                .company(user.getCompany())
                .companyEmail(user.getCompanyEmail())
                .isEmailCertified(false)
                .introduction(user.getIntroduction())
                .build();
    }
}
