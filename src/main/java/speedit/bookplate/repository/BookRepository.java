package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedit.bookplate.domain.Book;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
        Optional<Book> findByItemId(Long itemId);
        Boolean existsByItemId(Long itemId);
        Optional<Book> findById(Long id);
}
