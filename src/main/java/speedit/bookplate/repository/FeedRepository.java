package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.Feed;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findById(Long id);
    List<Feed> findAllByOrderByIdDesc();
    Optional<List<Feed>> findByBook(Book book);
    List<Feed> findAllBy();

    @Query(value = "select f from Feed f where f.user.id in (select t.followingId from Following t where t.followerId=:followerId)",nativeQuery = true)
    Optional<List<Feed>> findFollowingUserFeed(@Param("followerId")long follwerId);

    @Query(value = "select f from Feed f where f.user.id =:userId",nativeQuery = true)
    Optional<List<Feed>> findAllByUserId(@Param("userId")long userId);

    @Query(value = "select f from Feed f join fetch f.user u on u.id=f.user.id where job=:job",nativeQuery = true)
    Optional<List<Feed>> findFeedByRelationJob(@Param("job")String job);

}
