package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.user.*;
import speedit.bookplate.exception.ExpireTokenException;
import speedit.bookplate.service.UserService;
import speedit.bookplate.utils.JwtService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation(value = "회원가입",notes = "회원가입 DTO로 회원가입")
    @RequestMapping(value = "/sign-up",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> SingUp(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok().body(userService.SignUp(userCreateRequest));
    }

    @ApiOperation(value = "로그인",notes = "닉네임, 비밀번호를 통해서 로그인")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok().body(userService.login(userLoginRequest));
    }

    @ApiOperation(value = "닉네임 체크",notes = "닉네임 입력 받아서 닉네임 중복 체크")
    @RequestMapping(value = "/nickname",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkNickname(@RequestBody UserNicknameRequest userNicknameRequest){

        userService.checkNickname(userNicknameRequest.getNickname());

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "이메일 체크",notes = "이메일 입력 받아서 이메일 중복 체크")
    @RequestMapping(value = "/email",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkEmail(@RequestBody UserEmailRequest userEmailRequest){

        userService.checkEmail(userEmailRequest.getEmail());

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "아이디 찾기",notes = "로그인 시 아이디 찾기")
    @RequestMapping(value = "/id",method = RequestMethod.POST)
    public ResponseEntity<UserIdResponse> findNickname(@RequestBody UserIdRequest userIdRequest){
        return ResponseEntity.ok(userService.findUserId(userIdRequest));
    }

    @ApiOperation(value = "비밀번호 찾기",notes = "로그인 시 비밀번호 찾기")
    @RequestMapping(value = "/password",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> findPassword(@Valid @RequestBody UserPasswordRequest userPasswordRequest){

        userService.findUserPassword(userPasswordRequest);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "로그인 된 유저 프로필 조회",notes = "JWT토큰으로 인증된 유저 프로필 정보 리턴")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public ResponseEntity<LoggedInUserResponse> getLoggedInUserProfile(){
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(userService.getUserProfile(jwtService.getUserIdx()));
    }

    @ApiOperation(value = "프로필 조회",notes = "유저 프로필 정보 리턴")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/profile/{userId}",method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable final Long userId){
        jwtService.isExpireAccessToken();
        Long loggedInUserId = jwtService.getUserIdx();
        return ResponseEntity.ok().body(userService.find(userId,loggedInUserId));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiOperation(value = "회원 탈퇴",notes = "JWT토큰으로 인증한 회원 탈퇴")
    @RequestMapping(value = "/inactive",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> deleteUser(){
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.deleteUser(userIdx);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "프로필 수정",notes = "JWT토큰으로 인증된 회원의 프로필 수정.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/profile",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> modifyProfile(@RequestBody UserRequest userRequest) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.modifyProfile(userIdx, userRequest);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "새로운 토큰 발급",notes = "refreshtoken으로 새로운 토큰 발급")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/token/refresh",method = RequestMethod.GET)
    public ResponseEntity<UserLoginResponse> getRefreshToken(){
        if(jwtService.isExpireRefreshToken()) {
            throw new ExpireTokenException();
        }

        return ResponseEntity.ok(userService.checkRefreshToken(jwtService.getRefreshToken()));
    }

    @ApiOperation(value = "유저 검색",notes = "직업으로 유저 검색하기")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public List<ProfileResponse> search(@RequestParam(value = "page")int page, @RequestParam(value = "job",required = false) String job){
        long userIdx = jwtService.getUserIdx();

        return userService.findBySearchConditions(page,job,userIdx);
    }


}
