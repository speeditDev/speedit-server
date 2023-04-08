package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.service.FeedLikeService;
import speedit.bookplate.dto.feedlike.FeedLikeRequsetDto;
import speedit.bookplate.utils.JwtService;

import javax.validation.Valid;

@Api(tags = {"3.FeedLike"})
@RequiredArgsConstructor
@RestController
@RequestMapping
public class FeedLikeController {

    private final FeedLikeService feedLikeService;
    private final JwtService jwtService;

    @ApiOperation(value = "피드 좋아요 하기", notes = "특정한 피드를 좋아요 한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "피드 좋아요에 성공하였습니다."),
            @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> likeFeed(@RequestBody @Valid FeedLikeRequsetDto feedLikeRequsetDto)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedLikeService.likeFeed(jwtService.getUserIdx(), feedLikeRequsetDto));
    }

    @ApiOperation(value = "피드 좋아요 취소하기", notes = "특정한 피드를 좋아를 취소 한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "피드 좋아요에 성공하였습니다."),
            @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/CancelLikeFeed",method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> cancelLikeFeed(@RequestBody @Valid FeedLikeRequsetDto feedLikeRequsetDto)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedLikeService.cancelLikeFeed(feedLikeRequsetDto));
    }
}
