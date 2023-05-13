package speedit.bookplate.repository;

import org.springframework.data.domain.Pageable;
import speedit.bookplate.domain.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByJobUsingQuerydsl(String job, Pageable pageable);
    List<Long> findIdByJobUsingQuerydsl(String job,Pageable pageable);
}
