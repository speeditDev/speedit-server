package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.dto.user.*;
import speedit.bookplate.domain.User;
import speedit.bookplate.exception.*;
import speedit.bookplate.repository.FollowingRepository;
import speedit.bookplate.repository.UserRepository;
import speedit.bookplate.utils.JwtService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    public CommonResponseDto SignUp(UserCreateRequest userCreateRequest){

        if(userRepository.existsByNicknameAndBirthAndJob(userCreateRequest.getNickname(), userCreateRequest.getBirth(), userCreateRequest.getJob()))
            throw new SameUserException();

        userRepository.save(User.of(userCreateRequest));

        return new CommonResponseDto();
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest){
        User loginUser = userRepository.findByNicknameAndPassword(userLoginRequest.getNickname(), userLoginRequest.getPassword())
                .orElseThrow(()->new WrongIdOrPasswordException());

        Long userIdx = loginUser.getId();

        String accessToken = jwtService.createAccessToken(userIdx);
        String refreshToken = jwtService.createRefreshToken(userIdx);

        UserLoginResponse userLoginResponse = new UserLoginResponse(accessToken,refreshToken);
        loginUser.setRefreshToken(refreshToken);

        return userLoginResponse;
    }

    public boolean checkNickname(String nickname){

        if(userRepository.existsByNickname(nickname))
            throw new DuplicateNicknameException();

        return false;
    }

    public boolean checkEmail(String email) {

        if(userRepository.existsByPersonalEmail(email))
            throw new DuplicationEmailException();

        return false;
    }

    public UserResponse find(final Long targetId,final Long loggedInId){
        final User user = findUser(targetId);
        final boolean following = isFollowing(loggedInId,targetId);
        return UserResponse.of(user,following,followerCnt(targetId));
    }

    private boolean isFollowing(final Long followerId,final Long followingId){
        return followingRepository.existsByFollowerIdAndFollowingId(followerId,followingId);
    }

    private int followerCnt(final Long userId){
        return followingRepository.findByFollowerId(userId).size();
    }

    public UserIdResponse findUserId(UserIdRequest userIdRequest){
        User user = userRepository.findByPersonalEmailAndBirth(userIdRequest.getEmail(), userIdRequest.getBirth())
                .orElseThrow(()-> new WrongEmailOrBirthException());
        return new UserIdResponse(user.getNickname(),user.getCreatedAt());
    }

    public void findUserPassword(UserPasswordRequest userPasswordRequest){
        User user = userRepository.findByNicknameAndPersonalEmailAndBirth(userPasswordRequest.getNickname(),userPasswordRequest.getEmail(),userPasswordRequest.getBirth())
                .orElseThrow(()-> new NotExistUserException());
        user.updatePassword(userPasswordRequest.getPassword());
    }

    public void deleteUser(long userIdx){
        User user = userRepository.findById(userIdx)
                .orElseThrow(()-> new NotExistUserException());
        user.isDelete();
    }

    public void modifyProfile(long userIdx, UserRequest userRequest){
        User user = findUser(userIdx);
        user.update(userRequest.toUser());
    }

    public LoggedInUserResponse getUserProfile(long userIdx){
        User user = findUser(userIdx);
        return LoggedInUserResponse.from(user,followerCnt(userIdx));
    }

    public User findUser(final Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new NotExistUserException());
    }

    public List<ProfileResponse> findBySearchConditions(int page,String job,long loggedInId){
        Pageable pageInfo = PageRequest.of(page,12);

        final List<User> userPage = userRepository.findByJob(job,pageInfo).getContent(); //검색을 통해서 필요한 유저 리스트

        List<Feed> feeds = userRepository.findByFetchJoin(userPage);

        return feeds.stream()
                .map(v-> v.getUser())
                .map(v-> ProfileResponse.from(v,false))
                .collect(Collectors.toList());
    }

    /*
    public List<ProfileResponse> createProfiles(final Long loggedInId, final List<User> users){
        return users.stream()
                .map(v-> ProfileResponse.from(v,isFollowing(loggedInId,v.getId())))
                .collect(Collectors.toList());
    }*/

}
