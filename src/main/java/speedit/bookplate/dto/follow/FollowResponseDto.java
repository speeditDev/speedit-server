package speedit.bookplate.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponseDto {

    private String profileImg;

    private String nickname;

    private String job;

    private String company;

    public static FollowResponseDto createFollow(String profileImg, String nickname, String job, String company){
        FollowResponseDto followResponseDto = new FollowResponseDto(profileImg,nickname,job,company);
        return followResponseDto;
    }

}
