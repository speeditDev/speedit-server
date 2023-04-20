package speedit.bookplate.dto.user;

import lombok.*;
import speedit.bookplate.dto.feed.FeedResponseDto;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponseDto {

    private String nickname;

    private String profileImg;

    private String birth;

    private String gender;

    private String job;

    private String company;

    private String companyEmail;

    private boolean isEmailCertified;

    private String introduction;

    private List<FeedResponseDto> feeds;
}
