package speedit.bookplate.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedLikeResponseDto {

    private Long likes;
    private Boolean liked;

}
