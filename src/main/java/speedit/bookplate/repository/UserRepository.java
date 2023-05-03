package speedit.bookplate.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

    List<User> findAllBy();


}
