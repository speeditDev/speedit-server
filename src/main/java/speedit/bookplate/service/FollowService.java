package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.domain.User;
import speedit.bookplate.exception.AlreadyFollowingException;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.exception.NotFollowingException;
import speedit.bookplate.repository.FollowingRepository;
import speedit.bookplate.dto.follow.ProfileResponse;
import speedit.bookplate.domain.Following;
import speedit.bookplate.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowingRepository followingRepository;
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
        followingRepository.save(following);
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
        followingRepository.delete(following);
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
        if(followingRepository.existsByFollowerIdAndFollowingId(followerId,followingId)){
            throw new AlreadyFollowingException();
        }
    }

    private Following findFollowingRelation(final Long followerId,final Long followingId){
        return followingRepository.findByFollowingIdAndAndFollowerId(followingId,followerId)
                .orElseThrow(()->new NotFollowingException());
    }

    public List<ProfileResponse> getFollowing(long userIdx){
        List<Following> follows = followingRepository.findByFollowerId(userIdx);
        List<ProfileResponse> array = new ArrayList<>();
        follows.stream().map(v ->
                array.add(
                ProfileResponse.createFollow(userRepository.findById(v.getFollowingId()).get().getId(),
                        userRepository.findById(v.getFollowingId()).get().getProfileImg(),
                        userRepository.findById(v.getFollowingId()).get().getNickname(),
                        userRepository.findById(v.getFollowingId()).get().getJob(),
                        userRepository.findById(v.getFollowingId()).get().getCompany(),
                        userRepository.findById(v.getFollowingId()).get().getFollowerCount(),
                        false))
                );
        return array;
    }

    public List<ProfileResponse> getFollower(long userIdx) {
        List<Following> follows= followingRepository.findByFollowingId(userIdx);
        List<ProfileResponse> array = new ArrayList<>();
        follows.stream().map(v ->
                array.add(
                        ProfileResponse.createFollow(userRepository.findById(v.getFollowingId()).get().getId(),
                                userRepository.findById(v.getFollowingId()).get().getProfileImg(),
                                userRepository.findById(v.getFollowingId()).get().getNickname(),
                                userRepository.findById(v.getFollowingId()).get().getJob(),
                                userRepository.findById(v.getFollowingId()).get().getCompany(),
                                userRepository.findById(v.getFollowingId()).get().getFollowerCount(),
                                false))
        );
        return array;
    }

}
