package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedit.bookplate.domain.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>{

}
