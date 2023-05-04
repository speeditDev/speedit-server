package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private String nickname;
    private String profileImg;
    private List<FeedResponse> profileFeeds;
    private int followerCount;
    private boolean following;

    public static ProfileResponse from(final User user,final boolean isFollowing){
        return ProfileResponse.builder()
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .profileFeeds(convertToProfileFeedsResponse(user.getFeeds()))
                .followerCount(user.getFollowerCount())
                .following(isFollowing)
                .build();
    }

    private static List<FeedResponse> convertToProfileFeedsResponse(final List<Feed> feeds){
        return feeds.stream()
                .map(v -> FeedResponse.from(v))
                .collect(Collectors.toList());
    }


}
