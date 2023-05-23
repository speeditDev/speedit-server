package speedit.bookplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    Optional<Feed> findById(Long id);

    Optional<List<Feed>> findByBook(Book book);

    @Query(value = "select f from Feed f join fetch f.book b join fetch f.user where b.category=:bookCategory")
    Optional<List<Feed>> findFeedByBookCategory(String bookCategory,Pageable pageable);

    @Query(value = "select f from Feed f join fetch f.book join fetch f.user")
    List<Feed> findWithPagination(Pageable pageable);

    @Query(value = "select *,(select count(1) from feed_like as fl where fl.id = f.id) as f_count from feed f inner join book where f.user_id in (select t.following_id from following as t where t.follower_id=:followerId) order by f_count desc, f.id desc",nativeQuery = true)
    Optional<List<Feed>> findFollowingUserFeed(@Param("followerId")long follwerId,Pageable pageable);

    @Query(value = "select f from Feed f join fetch f.user u join fetch f.book b where u.job=:job")
    Optional<List<Feed>> findRelationJob(@Param("job")String job,Pageable pageable);

    @Query("select f from Feed f join fetch f.book where f.user in :userArr")
    List<Feed> findByFetchJoin(List<User> userArr);

    @Query(value = "select f from Feed f join fetch f.book where f.user.id in :userArr")
    Optional<List<Feed>> findRelationJobByFetchJoin(List<Long> userArr,Pageable pageable);

}
