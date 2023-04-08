package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.service.FollowService;
import speedit.bookplate.dto.follow.FollowResponseDto;
import speedit.bookplate.utils.JwtService;

import java.util.List;

@Controller
@AllArgsConstructor
@Api(tags = {"4. follow"})
@RequestMapping
public class FollowController {

    private FollowService followService;
    private JwtService jwtService;

    @ApiOperation(value = "팔로잉",notes = "팔로잉 하고자 하는 유저 Idx값을 이용해서 팔로잉한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "팔로우 등록에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/following/{memberId}",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> follow(@PathVariable("memberId")Long followingId){
            jwtService.isExpireAccessToken();
            long followerId=jwtService.getUserIdx();

            followService.follow(followerId,followingId);
            return ResponseEntity.ok(new CommonResponseDto());
    }


    @ApiOperation(value = "팔로잉 취소",notes = "팔로잉 취소하고자 하는 유저 Idx값을 이용해서 팔로잉 취소한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "팔로우 취소에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/following/{memberId}",method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponseDto> unfollow(@PathVariable("memberId")Long followingId){
            jwtService.isExpireAccessToken();
            long followerId=jwtService.getUserIdx();

            followService.unfollow(followerId,followingId);

            return ResponseEntity.ok(new CommonResponseDto());
    }


    @ApiResponses({
            @ApiResponse(code=200,message = "요청에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiOperation(value = "팔로잉 목록 조회",notes = "JWT토큰으로 인증된 회원이 팔로잉한 유저 목록을 보여준다.")
    @RequestMapping(value = "/following",method = RequestMethod.GET)
    public ResponseEntity<List<FollowResponseDto>> getFollowing(){
        jwtService.isExpireAccessToken();
        long userId = jwtService.getUserIdx();

        List<FollowResponseDto> followResponseDtos = followService.getFollowing(userId);

        return ResponseEntity.ok(followResponseDtos);
    }

    @ApiOperation(value = "팔로워 목록 조회",notes = "JWT 토큰으로 인증된 회원을 팔로잉한 팔로워 목록을 보여준다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "요청에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/follower",method = RequestMethod.GET)
    public ResponseEntity<List<FollowResponseDto>> getFollower(){
        jwtService.isExpireAccessToken();
        long userId = jwtService.getUserIdx();

        List<FollowResponseDto> followResponseDtos = followService.getFollower(userId);

        return ResponseEntity.ok().body(followResponseDtos);
    }

}
