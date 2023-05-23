package speedit.bookplate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.feed.*;
import speedit.bookplate.exception.NotExistCodeException;
import speedit.bookplate.service.FeedService;
import speedit.bookplate.utils.JwtService;
import speedit.bookplate.utils.enumTypes.Code;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = {"2. Feed"})
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    private final JwtService jwtService;

    @ApiOperation(value = "피드 작성하기",notes = "새로운 피드를 작성한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> createFeed(@RequestBody @Valid FeedRequestDto feedRequestDto) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        feedService.createFeed(userIdx, feedRequestDto);
        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "피드 삭제하기",notes = "입력된 feedIdx의 피드를 삭제한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/{feedIdx}",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> deleteFeed(@PathVariable long feedIdx) {
        jwtService.isExpireAccessToken();
        feedService.deleteFeed(feedIdx);

        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "피드 수정하기",notes = "입력된 feedIdx의 피드를 수정한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> updateFeed(@RequestBody @Valid FeedUpdateRequestDto feedUpdateRequestDto) {
        jwtService.isExpireAccessToken();
        feedService.updateFeed(feedUpdateRequestDto);

        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "피드 조회하기",notes = "분류별로 피드를 조회할 수 있다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<List<FeedResponseDto>> getFeed(   @RequestParam(value = "code",required = false) Code code,
                                                            @RequestParam(value = "job",required = false) String job,
                                                            @RequestParam(value = "category",required = false) String category,
                                                            @RequestParam(value = "page")int page){
            if(!Code.isExistCode(code)){
                throw new NotExistCodeException();
            }

            jwtService.isExpireAccessToken();
            long userIdx = 3L;

            return ResponseEntity.ok().body(feedService.getFeed(userIdx,category,code,job,page));
    }

    @ApiOperation(value = "피드 조회하기",notes = "모든 피드를 조회한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<FeedResponseDto>> searchFeed(){

        List<FeedResponseDto> searchFeedRes = feedService.getFeeds();

        return ResponseEntity.ok().body(searchFeedRes);
    }

    @ApiOperation(value = "피드 좋아요",notes = "피드를 좋아요한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/{feedIdx}/likes",method = RequestMethod.POST)
    public ResponseEntity<FeedLikeResponseDto> likeFeed(@PathVariable final Long feedIdx)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedService.likeFeed(jwtService.getUserIdx(), feedIdx));
    }

    @ApiOperation(value = "피드 좋아요 취소",notes = "피드 좋아요를 취소한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @RequestMapping(value = "/{feedIdx}/likes",method = RequestMethod.DELETE)
    public ResponseEntity<FeedLikeResponseDto> cancelLikeFeed(@PathVariable final Long feedIdx)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedService.cancelLikeFeed(jwtService.getUserIdx(),feedIdx));
    }

}
