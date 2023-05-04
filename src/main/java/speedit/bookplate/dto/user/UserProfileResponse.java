package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.domain.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {

    private String nickname;
    private String profileImg;
    private String job;
    private String company;
    private String introduction;
    private int followerCount;
    private boolean following;
    private String contents;
    private String title;
    private String thumbnail;

    public static UserProfileResponse of(final User user, final boolean following, final Book book, final Feed feed){
        return UserProfileResponse.builder()
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .job(user.getJob())
                .company(user.getCompany())
                .introduction(user.getIntroduction())
                .followerCount(user.getFollowerCount())
                .following(following)
                .contents(feed.getContents())
                .title(book.getTitle())
                .thumbnail(book.getThumbnail())
                .build();
    }

}
