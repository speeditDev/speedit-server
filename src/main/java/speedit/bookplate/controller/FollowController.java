package speedit.bookplate.controller;

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
@RequestMapping
public class FollowController {

    private final FollowService followService;
    private final JwtService jwtService;

    @RequestMapping(value = "/following/{memberId}",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> follow(@PathVariable("memberId")Long followingId){
            jwtService.isExpireAccessToken();
            long followerId=jwtService.getUserIdx();

            followService.follow(followerId,followingId);
            return ResponseEntity.ok(new CommonResponseDto());
    }


    @RequestMapping(value = "/following/{memberId}",method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponseDto> unfollow(@PathVariable("memberId")Long followingId){
            jwtService.isExpireAccessToken();
            long followerId=jwtService.getUserIdx();

            followService.unfollow(followerId,followingId);

            return ResponseEntity.ok(new CommonResponseDto());
    }


    @RequestMapping(value = "/followings",method = RequestMethod.GET)
    public ResponseEntity<List<ProfileResponse>> getByFollowings(){
        jwtService.isExpireAccessToken();
        long userId = jwtService.getUserIdx();

        List<ProfileResponse> profileResponses = followService.getFollowing(userId);

        return ResponseEntity.ok(profileResponses);
    }


    @RequestMapping(value = "/follower",method = RequestMethod.GET)
    public ResponseEntity<List<ProfileResponse>> getFollower(){
        jwtService.isExpireAccessToken();
        long userId = jwtService.getUserIdx();

        List<ProfileResponse> profileResponses = followService.getFollower(userId);

        return ResponseEntity.ok().body(profileResponses);
    }

}
