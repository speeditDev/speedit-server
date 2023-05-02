package speedit.bookplate.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import speedit.bookplate.dto.user.UserCreateRequest;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.service.UserService;
import speedit.bookplate.utils.enumTypes.Gender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/*
public class UserServiceTest extends ServiceTest{


    @Test
    @DisplayName("회원을 id값을 통해 제거한다.")
    void delete() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest("eun02323","eun2938!","https://image.png","1997.09.23", Gender.W,"developer","kakao","eun0232@naver.com");
        userService.SignUp(userCreateRequest);

        // when
        Long userId=userRepository.findByPersonalEmailAndBirth("eun0232@naver.com","1997.09.23").get().getId();
        userService.deleteUser(userId);

        // then
        assertThatThrownBy(() -> userService.findUser(userId))
                .isInstanceOf(NotExistUserException.class);
    }



}
*/