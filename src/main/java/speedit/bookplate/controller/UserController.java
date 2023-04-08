package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
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

@Controller
@AllArgsConstructor
@Api(tags = {"1. User"})
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @ApiOperation(value = "회원가입",notes = "회원가입 DTO로 회원가입")
    @ApiResponses({
            @ApiResponse(code=200,message = "요청에 성공하였습니다."),
            @ApiResponse(code=406,message = "닉네임을 입력해주세요."),
            @ApiResponse(code=407,message = "닉네임 형식이 올바르지 않습니다."),
            @ApiResponse(code=409,message = "직업을 입력해주세요."),
            @ApiResponse(code = 414,message = "이미 등록된 유저입니다.")
    })
    @RequestMapping(value = "/sign-up",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> SingUp(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.ok().body(userService.SignUp(userCreateRequestDto));
    }

    @ApiOperation(value = "로그인",notes = "닉네임, 비밀번호를 통해서 로그인")
    @ApiResponses({
            @ApiResponse(code = 200,message = "로그인에 성공하였습니다.")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        return ResponseEntity.ok().body(userLoginResponseDto);
    }

    @ApiOperation(value = "닉네임 체크",notes = "닉네임 입력 받아서 닉네임 중복 체크")
    @ApiResponses({
            @ApiResponse(code=200,message = "사용 가능한 닉네임입니다."),
            @ApiResponse(code=408,message = "중복된 닉네임입니다.")
    })
    @RequestMapping(value = "/nickname",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkNickname(@RequestBody UserNicknameRequestDto userNicknameRequestDto){
        jwtService.isExpireAccessToken();

        if(userService.checkNickname(userNicknameRequestDto.getNickname())){
            throw new DuplicateNicknameException();
        }
        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "이메일 체크",notes = "이메일 입력 받아서 이메일 중복 체크")
    @ApiResponses({
            @ApiResponse(code=200,message = "사용 가능한 이메일입니다"),
            @ApiResponse(code=423,message = "중복된 이메일입니다.")
    })
    @RequestMapping(value = "/email",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkEmail(@RequestBody UserEmailRequestDto userEmailRequestDto){
        jwtService.isExpireAccessToken();

        if(userService.checkEmail(userEmailRequestDto.getEmail())){
            throw new DuplicationEmailException();
        }

        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "아이디 찾기",notes = "로그인 시 아이디 찾기")
    @ApiResponses({
            @ApiResponse(code=200,message = "아이디 찾기 성공"),
            @ApiResponse(code=423,message = "일치하는 회원정보가 없습니다")
    })
    @RequestMapping(value = "/Id",method = RequestMethod.POST)
    public ResponseEntity<UserIdResponseDto> findNickname(@RequestBody UserIdRequestDto userIdRequestDto){
        return ResponseEntity.ok(userService.findUserId(userIdRequestDto));
    }

    @ApiOperation(value = "프로필 조회",notes = "JWT토큰으로 인증된 유저 프로필 정보 리턴")
    @ApiResponses({
            @ApiResponse(code=200,message = "요청에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/profiles",method = RequestMethod.GET)
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestParam(required = false,defaultValue = "0") long idx){
            jwtService.isExpireAccessToken();
            long userIdx = jwtService.getUserIdx();
            if(idx==0){
                idx=userIdx;
            }
            return ResponseEntity.ok().body(userService.getUserProfile(idx));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiOperation(value = "회원 탈퇴",notes = "JWT토큰으로 인증한 회원 탈퇴")
    @ApiResponses({
            @ApiResponse(code=200,message = "회원 탈퇴에 성공했습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponseDto> deleteUser(){
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.deleteUser(userIdx);

        return ResponseEntity.ok().body(new CommonResponseDto());
    }


    @ApiOperation(value = "프로필 수정",notes = "JWT토큰으로 인증된 회원의 프로필 수정.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "프로필 수정에 성공했습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> modifyProfile(@RequestBody UserProfileRequestDto userProfileRequestDto) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();

        userService.modifyProfile(userIdx);

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
