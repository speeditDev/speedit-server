package speedit.bookplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByNicknameAndPassword(String nickname,String password);

    boolean existsByNicknameAndBirthAndJob(String nickname, String birth, String job);

    boolean existsByNickname(String name);

    boolean existsByPersonalEmail(String personalEmail);

    Optional<User> findById(Long id);

    Optional<User> findByPersonalEmailAndBirth(String personalEmail,String birth);

    Optional<User> findByNicknameAndPersonalEmailAndBirth(String nickname,String personalEmail,String birth);

    Page<User> findByJob(String job,Pageable pageable);

    Page<User> findByIdIn(List<Long> userIdArr,Pageable pageable);


}
