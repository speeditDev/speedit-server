package speedit.bookplate.domain;

import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class FeedLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "feed_Id")
    private Long feedId;

    @Version
    private Long version;

    public static FeedLike createLike(Long userId, Long feedId) {
        return FeedLike.builder()
                .feedId(feedId)
                .userId(userId)
                .build();
    }


}
