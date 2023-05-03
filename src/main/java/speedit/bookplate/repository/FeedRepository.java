package speedit.bookplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.Feed;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    Optional<Feed> findById(Long id);

    Page<Feed> findAllByOrderByIdDesc(Pageable pageable);

    Optional<List<Feed>> findByBook(Book book);

    Optional<Page<Feed>> findFeedByBookCategory(String bookCategory,Pageable pageable);

    Page<Feed> findAllBy(Pageable pageable);

    @Query(value = "select f from Feed f where f.user.id in (select t.followingId from Following t where t.followerId=:followerId)")
    Optional<List<Feed>> findFollowingUserFeed(@Param("followerId")long follwerId,Pageable pageable);

    @Query(value = "select f from Feed f inner join f.user u on f.user.id=u.id where u.job=:job")
    Optional<List<Feed>> findRelationJob(@Param("job")String job,Pageable pageable);

}
