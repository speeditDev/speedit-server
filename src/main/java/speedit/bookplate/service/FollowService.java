package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.domain.User;
import speedit.bookplate.exception.AlreadyFollowingException;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.exception.NotFollowingException;
import speedit.bookplate.repository.FollowRepository;
import speedit.bookplate.dto.follow.FollowResponseDto;
import speedit.bookplate.domain.Following;
import speedit.bookplate.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(long followerId,long followingId){
        validateUserExists(followerId);
        validateUserExists(followingId);
        validateNotFollowing(followerId,followingId);
        final Following following = Following.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();
        followRepository.save(following);
        User user = userRepository.findById(followerId)
                .orElseThrow(()->new NotExistUserException());
        user.increaseFollowerCnt();
    }

    @Transactional
    public void unfollow(long followerId,long followingId){
        validateUserExists(followerId);
        validateUserExists(followingId);
        findFollowingRelation(followerId,followingId);
        final Following following = Following.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();
        followRepository.delete(following);
        User user = userRepository.findById(followerId)
                .orElseThrow(()->new NotExistUserException());
        user.decreaseFollowerCnt();
    }

    private void validateUserExists(final Long userId){
        if(!userRepository.existsById(userId)){
            throw new NotExistUserException();
        }
    }

    private void validateNotFollowing(final Long followerId, final Long followingId){
        if(followRepository.existsByFollowerIdAndFollowingId(followerId,followingId)){
            throw new AlreadyFollowingException();
        }
    }

    private Following findFollowingRelation(final Long followerId,final Long followingId){
        return followRepository.findByFollowingIdAndAndFollowerId(followingId,followerId)
                .orElseThrow(()->new NotFollowingException());
    }

    public List<FollowResponseDto> getFollowing(long userIdx){
        List<Following> follows = followRepository.findByFollowerId(userIdx);
        List<FollowResponseDto> array = new ArrayList<>();
        follows.stream().map(v ->
                array.add(
                FollowResponseDto.createFollow(userRepository.findById(v.getFollowingId()).get().getProfileImg(),
                        userRepository.findById(v.getFollowingId()).get().getNickname(),
                        userRepository.findById(v.getFollowingId()).get().getJob(),
                        userRepository.findById(v.getFollowingId()).get().getCompany())));
        return array;
    }

    public List<FollowResponseDto> getFollower(long userIdx) {
        List<Following> follows= followRepository.findByFollowingId(userIdx);
        List<FollowResponseDto> array = new ArrayList<>();
        follows.stream().map(v ->
                array.add(
                        FollowResponseDto.createFollow(userRepository.findById(v.getFollowingId()).get().getProfileImg(),
                                userRepository.findById(v.getFollowingId()).get().getNickname(),
                                userRepository.findById(v.getFollowingId()).get().getJob(),
                                userRepository.findById(v.getFollowingId()).get().getCompany())));
        return array;
    }

}
