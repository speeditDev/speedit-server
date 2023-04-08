package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.user.*;
import speedit.bookplate.domain.User;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.exception.SameUserException;
import speedit.bookplate.exception.WrongIdOrPasswordException;
import speedit.bookplate.repository.UserRepository;
import speedit.bookplate.utils.JwtService;
import java.util.ArrayList;
import java.util.List;

import static speedit.bookplate.domain.User.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public CommonResponseDto SignUp(UserCreateRequestDto userCreateRequestDto){
        User userReq = SignUpUser(userCreateRequestDto);

        if(userRepository.existsByNicknameAndBirthAndJob(userCreateRequestDto.getNickname(), userCreateRequestDto.getBirth(), userCreateRequestDto.getJob()))
            throw new SameUserException();

        userRepository.save(userReq);

        return new CommonResponseDto();
    }

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto){
        User loginUser = userRepository.findByNickname(userLoginRequestDto.getNickname())
                .orElseThrow(()->new WrongIdOrPasswordException());

        Long userIdx = loginUser.getId();

        String accessToken = jwtService.createAccessToken(userIdx);
        String refreshToken = jwtService.createRefreshToken(userIdx);

        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(accessToken,refreshToken);
        loginUser.setRefreshToken(refreshToken);

        return userLoginResponseDto;
    }

    public boolean checkNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByPersonalEmail(email);
    }

    public UserIdResponseDto findUserId(UserIdRequestDto userIdRequestDto){
        User user = userRepository.findByPersonalEmailAndBirth(userIdRequestDto.getEmail(), userIdRequestDto.getBirth());
        return new UserIdResponseDto(user.getNickname(),user.getCreatedAt());
    }

    public void deleteUser(long userIdx){
        userRepository.delete(findUser(userIdx));
    }

    public void modifyProfile(long userIdx){
        User user = findUser(userIdx);
        user.update(user);
    }

    public UserProfileResponse getUserProfile(long userIdx){
        return converUserProfile(findUser(userIdx));
    }

    private User findUser(final Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new NotExistUserException());
    }

}
