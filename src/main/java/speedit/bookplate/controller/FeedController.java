package speedit.bookplate.controller;

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
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    private final JwtService jwtService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> createFeed(@RequestBody @Valid FeedRequestDto feedRequestDto) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        feedService.createFeed(userIdx, feedRequestDto);
        return ResponseEntity.ok(new CommonResponseDto());
    }


    @RequestMapping(value = "/{feedIdx}",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> deleteFeed(@PathVariable long feedIdx) {
        jwtService.isExpireAccessToken();
        feedService.deleteFeed(feedIdx);

        return ResponseEntity.ok(new CommonResponseDto());
    }


    @RequestMapping(value = "",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> updateFeed(@RequestBody @Valid FeedUpdateRequestDto feedUpdateRequestDto) {
        jwtService.isExpireAccessToken();
        feedService.updateFeed(feedUpdateRequestDto);

        return ResponseEntity.ok(new CommonResponseDto());
    }


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<List<FeedResponseDto>> getFeed(   @RequestParam(value = "code",required = false) Code code,
                                                            @RequestParam(value = "job",required = false) String job,
                                                            @RequestParam(value = "category",required = false) String category,
                                                            @RequestParam(value = "page")int page){
            if(!Code.isExistCode(code)){
                throw new NotExistCodeException();
            }

            //jwtService.isExpireAccessToken();
            long userIdx = 3L;

            return ResponseEntity.ok().body(feedService.getFeed(userIdx,category,code,job,page));
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<FeedResponseDto>> searchFeed(){

        List<FeedResponseDto> searchFeedRes = feedService.getFeeds();

        return ResponseEntity.ok().body(searchFeedRes);
    }

    @RequestMapping(value = "/{feedIdx}/likes",method = RequestMethod.POST)
    public ResponseEntity<FeedLikeResponseDto> likeFeed(@PathVariable final Long feedIdx)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedService.likeFeed(jwtService.getUserIdx(), feedIdx));
    }

    @RequestMapping(value = "/{feedIdx}/likes",method = RequestMethod.DELETE)
    public ResponseEntity<FeedLikeResponseDto> cancelLikeFeed(@PathVariable final Long feedIdx)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedService.cancelLikeFeed(jwtService.getUserIdx(),feedIdx));
    }

}
