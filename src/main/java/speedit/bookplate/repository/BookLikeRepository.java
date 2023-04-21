package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedit.bookplate.domain.BookLike;

import java.util.Optional;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
    BookLike findByBookId(Long bookId);
    boolean existsByUserIdAndBookId(Long userId,Long bookId);
    Optional<BookLike> findByUserIdAndBookId(Long userId,Long bookId);
}
