package speedit.bookplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.user.*;
import speedit.bookplate.exception.DuplicateNicknameException;
import speedit.bookplate.exception.DuplicationEmailException;
import speedit.bookplate.exception.ExpireTokenException;
import speedit.bookplate.service.UserService;
import speedit.bookplate.utils.JwtService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @RequestMapping(value = "/sign-up",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> SingUp(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok().body(userService.SignUp(userCreateRequest));
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok().body(userService.login(userLoginRequest));
    }

    @RequestMapping(value = "/nickname",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkNickname(@RequestBody UserNicknameRequest userNicknameRequest){

        if(userService.checkNickname(userNicknameRequest.getNickname())){
            throw new DuplicateNicknameException();
        }
        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @RequestMapping(value = "/email",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkEmail(@RequestBody UserEmailRequest userEmailRequest){

        if(userService.checkEmail(userEmailRequest.getEmail())){
            throw new DuplicationEmailException();
        }

        return ResponseEntity.ok().body(new CommonResponseDto());
    }


    @RequestMapping(value = "/id",method = RequestMethod.POST)
    public ResponseEntity<UserIdResponse> findNickname(@RequestBody UserIdRequest userIdRequest){
        return ResponseEntity.ok(userService.findUserId(userIdRequest));
    }


    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public ResponseEntity<LoggedInUserResponse> getLoggedInUserProfile(){
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(userService.getUserProfile(jwtService.getUserIdx()));
    }

    @RequestMapping(value = "/profile/{userId}",method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable final Long userId){
        jwtService.isExpireAccessToken();
        Long loggedInUserId = jwtService.getUserIdx();
        return ResponseEntity.ok().body(userService.find(userId,loggedInUserId));
    }

    @RequestMapping(value = "/inactive",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> deleteUser(){
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.deleteUser(userIdx);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }


    @RequestMapping(value = "/profile",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> modifyProfile(@RequestBody UserRequest userRequest) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.modifyProfile(userIdx, userRequest);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @RequestMapping(value = "/token/refresh",method = RequestMethod.GET)
    public ResponseEntity<UserLoginResponse> getRefreshToken(){
        if(jwtService.isExpireRefreshToken()) {
            throw new ExpireTokenException();
        }
        Long userIdx = jwtService.getUserIdxUsingRefreshToken();

        return ResponseEntity.ok(new UserLoginResponse(jwtService.createAccessToken(userIdx),jwtService.createRefreshToken(userIdx)));
    }







}
