package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedit.bookplate.domain.BookLike;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
    BookLike findByBookId(Long bookId);
}
