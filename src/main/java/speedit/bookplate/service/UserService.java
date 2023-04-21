package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.user.*;
import speedit.bookplate.domain.User;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.exception.SameUserException;
import speedit.bookplate.exception.WrongEmailOrBirthException;
import speedit.bookplate.exception.WrongIdOrPasswordException;
import speedit.bookplate.repository.UserRepository;
import speedit.bookplate.utils.JwtService;

import static speedit.bookplate.domain.User.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public CommonResponseDto SignUp(UserCreateRequest userCreateRequest){

        if(userRepository.existsByNicknameAndBirthAndJob(userCreateRequest.getNickname(), userCreateRequest.getBirth(), userCreateRequest.getJob()))
            throw new SameUserException();

        userRepository.save(SignUpUser(userCreateRequest));

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
        return userRepository.existsByNickname(nickname);
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByPersonalEmail(email);
    }

    public UserIdResponseDto findUserId(UserIdRequest userIdRequest){
        User user = userRepository.findByPersonalEmailAndBirth(userIdRequest.getEmail(), userIdRequest.getBirth())
                .orElseThrow(()-> new WrongEmailOrBirthException());
        return new UserIdResponseDto(user.getNickname(),user.getCreatedAt());
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

    public UserProfileResponse getUserProfile(long userIdx){
        User user = findUser(userIdx);
        return UserProfileResponse.from(user);
    }

    private User findUser(final Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new NotExistUserException());
    }

}
