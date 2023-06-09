package speedit.bookplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import speedit.bookplate.domain.Following;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following,Long> {
    boolean existsByFollowerIdAndFollowingId(Long followerId,Long followingId);
    List<Following> findByFollowerId(Long followerId);
    List<Following> findByFollowingId(Long followingId);
    Optional<Following> findByFollowingIdAndAndFollowerId(Long followingId,Long followerId);
    List<Following> findByFollowerIdAndFollowingIdIn(Long followerId, List<Long> followingIds);
}
