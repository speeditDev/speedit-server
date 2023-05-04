package speedit.bookplate.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import speedit.bookplate.dto.user.UserCreateRequest;
import speedit.bookplate.exception.SameUserException;
import speedit.bookplate.utils.enumTypes.Gender;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest extends ServiceTest{
    
    @Test
    public void 중복된_닉네임_직업_생년월일_유저가_있으면_예외반환(){
        String nickname = "이은영";
        String birth = "19980923";
        String job = "개발자";

        UserCreateRequest request = new UserCreateRequest(nickname,"1234","image.png",birth, Gender.W,job,"kakao","eun0234");

        when(userRepository.existsByNicknameAndBirthAndJob(anyString(),anyString(),anyString())).thenReturn(true);

        Assertions.assertThrows(SameUserException.class, ()-> userService.SignUp(request));
    }


}
