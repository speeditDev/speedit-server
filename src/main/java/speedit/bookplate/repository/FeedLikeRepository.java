package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import speedit.bookplate.domain.FeedLike;

import javax.persistence.LockModeType;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    boolean existsByFeedIdAndUserId(Long feedId, Long userId);

    @Lock(LockModeType.OPTIMISTIC)
    FeedLike findByFeedId(Long feedId);
}
