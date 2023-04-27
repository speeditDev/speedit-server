package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.Feed;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findById(Long id);
    List<Feed> findAllByOrderByIdDesc();
    Optional<List<Feed>> findByBook(Book book);
    List<Feed> findAllBy();
}
