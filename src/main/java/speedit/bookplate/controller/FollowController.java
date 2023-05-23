package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.service.FollowService;
import speedit.bookplate.dto.follow.ProfileResponse;
import speedit.bookplate.utils.JwtService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"4. Follow"})
@RequestMapping
public class FollowController {

    private final FollowService followService;
    private final JwtService jwtService;

    @ApiOperation(value = "팔로잉",notes = "팔로잉 하고자 하는 유저 Idx값을 이용해서 팔로잉한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/following/{memberId}",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> follow(@PathVariable("memberId")Long followingId){
            jwtService.isExpireAccessToken();
            long followerId=jwtService.getUserIdx();

            followService.follow(followerId,followingId);
            return ResponseEntity.ok(new CommonResponseDto());
    }


    @ApiOperation(value = "팔로잉 취소",notes = "팔로잉 취소하고자 하는 유저 Idx값을 이용해서 팔로잉 취소한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/following/{memberId}",method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponseDto> unfollow(@PathVariable("memberId")Long followingId){
            jwtService.isExpireAccessToken();
            long followerId=jwtService.getUserIdx();

            followService.unfollow(followerId,followingId);

            return ResponseEntity.ok(new CommonResponseDto());
    }


    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiOperation(value = "팔로잉 목록 조회",notes = "JWT토큰으로 인증된 회원이 팔로잉한 유저 목록을 보여준다.")
    @RequestMapping(value = "/followings",method = RequestMethod.GET)
    public ResponseEntity<List<ProfileResponse>> getByFollowings(@RequestParam(value = "page")int page){
        jwtService.isExpireAccessToken();
        long userId = jwtService.getUserIdx();

        List<ProfileResponse> profileResponses = followService.getFollowing(userId,page);

        return ResponseEntity.ok(profileResponses);
    }

    @ApiOperation(value = "팔로워 목록 조회",notes = "JWT 토큰으로 인증된 회원을 팔로잉한 팔로워 목록을 보여준다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/followers",method = RequestMethod.GET)
    public ResponseEntity<List<ProfileResponse>> getFollower(@RequestParam(value = "page")int page){
        jwtService.isExpireAccessToken();
        long userId = jwtService.getUserIdx();

        List<ProfileResponse> profileResponses = followService.getFollower(userId,page);

        return ResponseEntity.ok().body(profileResponses);
    }

}
