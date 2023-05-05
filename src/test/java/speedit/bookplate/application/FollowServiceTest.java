package speedit.bookplate.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import speedit.bookplate.exception.NotExistUserException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class FollowServiceTest extends ServiceTest{

    @Test
    public void 존재하지_않는_유저_팔로잉시_예외반환(){

        when(userRepository.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrows(NotExistUserException.class,()->followService.follow(1l,2l));
    }


}
