package speedit.bookplate.domain;

import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
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

    public FeedLike(Long userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }
}
