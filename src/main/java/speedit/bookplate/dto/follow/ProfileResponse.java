package speedit.bookplate.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private Long id;
    private String profileImg;
    private String nickname;
    private String job;
    private String company;
    private int followerCount;
    private boolean following;

    public static ProfileResponse from(final User user){
        return new ProfileResponse(
             user.getId(),
             user.getProfileImg(),
             user.getNickname(),
             user.getJob().getTitle(),
             user.getCompany(),
             user.getFollowerCount(),
             false
        );
    }

    public static ProfileResponse createFollow(Long id,String profileImg,String nickname,String job,String company,int followerCount,boolean following){
        ProfileResponse profileResponse = new ProfileResponse(id,profileImg,nickname,job,company,followerCount,following);
        return profileResponse;
    }

}
