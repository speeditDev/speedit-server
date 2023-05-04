package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.Feed;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedResponse {

    private Long id;
    private String contents;
    private String bookTitle;

    public static FeedResponse from(final Feed feed){
        return FeedResponse.builder()
                .id(feed.getId())
                .contents(feed.getContents())
                .bookTitle(feed.getBook().getTitle())
                .build();
    }

}
