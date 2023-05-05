package speedit.bookplate.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import speedit.bookplate.dto.user.UserCreateRequest;
import speedit.bookplate.dto.user.UserIdRequest;
import speedit.bookplate.dto.user.UserLoginRequest;
import speedit.bookplate.dto.user.UserPasswordRequest;
import speedit.bookplate.exception.*;
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

    @Test
    public void 존재하지_않는_비밀번호와_아이디_입력시_에외반환() {
        UserLoginRequest request = new UserLoginRequest("eunyoung","eun1234");

        when(userRepository.findByNicknameAndPassword(anyString(),anyString())).thenThrow(new WrongIdOrPasswordException());

        Assertions.assertThrows(WrongIdOrPasswordException.class,()->userService.login(request));
    }

    @Test
    public void 이메일_중복시_예외반환(){

        when(userRepository.existsByPersonalEmail(anyString())).thenReturn(true);

        Assertions.assertThrows(DuplicationEmailException.class,()-> userService.checkEmail("eun0232323@naver.com"));
    }

    @Test
    public void 닉네임_중복시_예외반환(){

        when(userRepository.existsByNickname(anyString())).thenReturn(true);

        Assertions.assertThrows(DuplicateNicknameException.class,()->userService.checkNickname("eunyoung"));
    }

    @Test
    public void 아이디찾기시_존재하지_않는_유저예외반환(){

        when(userRepository.findByPersonalEmailAndBirth(anyString(),anyString())).thenThrow(new WrongEmailOrBirthException());

        Assertions.assertThrows(WrongEmailOrBirthException.class,()->userService.findUserId(new UserIdRequest("skdf","sdf")));
    }

    @Test
    public void 비밀번호찾기시_존재하지_않는_유저예외반환(){

        when(userRepository.findByNicknameAndPersonalEmailAndBirth(anyString(),anyString(),anyString())).thenThrow(new NotExistUserException());

        Assertions.assertThrows(NotExistUserException.class,()->userService.findUserPassword(new UserPasswordRequest("sdf","skdf","skdjf")));
    }


}
