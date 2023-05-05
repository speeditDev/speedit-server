package speedit.bookplate.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import speedit.bookplate.exception.AlreadyFollowingException;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.exception.NotFollowingException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class FollowServiceTest extends ServiceTest{

    @Test
    public void 존재하지_않는_유저_팔로잉시_예외반환(){

        when(userRepository.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrows(NotExistUserException.class,()->followService.follow(1l,2l));
    }

    @Test
    public void 이미_팔로잉했으면_팔로잉_불가능하도록_예외_반환(){
        //given
        Long followerId = 1l;
        Long followingId = 2l;
        given(userRepository.existsById(followerId)).willReturn(true);
        given(userRepository.existsById(followingId)).willReturn(true);

        //when
        when(followingRepository.existsByFollowerIdAndFollowingId(anyLong(),anyLong())).thenThrow(new AlreadyFollowingException());

        //then
        Assertions.assertThrows(AlreadyFollowingException.class,()->followService.follow(followerId,followingId));
    }

    @Test
    public void 팔로잉한_관계가_아니면_팔로잉취소불가능한_예외반환(){
        //given
        Long followerId = 1l;
        Long followingId = 2l;
        given(userRepository.existsById(followerId)).willReturn(true);
        given(userRepository.existsById(followingId)).willReturn(true);

        //when
        when(followingRepository.findByFollowingIdAndAndFollowerId(followingId,followerId)).thenThrow(new NotFollowingException());

        //then
        Assertions.assertThrows(NotFollowingException.class,()->followService.unfollow(followerId,followingId));
    }


}
