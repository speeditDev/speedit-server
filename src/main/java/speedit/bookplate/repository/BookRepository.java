package speedit.bookplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import speedit.bookplate.domain.Book;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
        Optional<Book> findByIsbn(Long isbn);
        Boolean existsByIsbn(Long isbn);
        Optional<Book> findById(Long id);
}
