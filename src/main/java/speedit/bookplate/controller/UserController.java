package speedit.bookplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.user.*;
import speedit.bookplate.exception.DuplicateNicknameException;
import speedit.bookplate.exception.DuplicationEmailException;
import speedit.bookplate.exception.ExpireTokenException;
import speedit.bookplate.service.UserService;
import speedit.bookplate.utils.JwtService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @RequestMapping(value = "/sign-up",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> SingUp(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.ok().body(userService.SignUp(userCreateRequestDto));
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto){
        return ResponseEntity.ok().body(userService.login(userLoginRequestDto));
    }

    @RequestMapping(value = "/nickname",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkNickname(@RequestBody UserNicknameRequestDto userNicknameRequestDto){

        if(userService.checkNickname(userNicknameRequestDto.getNickname())){
            throw new DuplicateNicknameException();
        }
        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @RequestMapping(value = "/email",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkEmail(@RequestBody UserEmailRequestDto userEmailRequestDto){

        if(userService.checkEmail(userEmailRequestDto.getEmail())){
            throw new DuplicationEmailException();
        }

        return ResponseEntity.ok().body(new CommonResponseDto());
    }


    @RequestMapping(value = "/id",method = RequestMethod.POST)
    public ResponseEntity<UserIdResponseDto> findNickname(@RequestBody UserIdRequestDto userIdRequestDto){
        return ResponseEntity.ok(userService.findUserId(userIdRequestDto));
    }


    @RequestMapping(value = "/profiles",method = RequestMethod.GET)
    public ResponseEntity<UserProfileResponseDto> getUserProfile(){
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(userService.getUserProfile(jwtService.getUserIdx()));
    }


    @RequestMapping(value = "/inactive",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> deleteUser(){
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.deleteUser(userIdx);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }


    @RequestMapping(value = "",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> modifyProfile(@RequestBody UserRequestDto userRequestDto) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.modifyProfile(userIdx,userRequestDto);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @RequestMapping(value = "/token/refresh",method = RequestMethod.GET)
    public ResponseEntity<UserLoginResponseDto> getRefreshToken(){
        if(jwtService.isExpireRefreshToken()) {
            throw new ExpireTokenException();
        }
        Long userIdx = jwtService.getUserIdxUsingRefreshToken();

        return ResponseEntity.ok(new UserLoginResponseDto(jwtService.createAccessToken(userIdx),jwtService.createRefreshToken(userIdx)));
    }

}
