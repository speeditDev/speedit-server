package speedit.bookplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.feed.*;
import speedit.bookplate.dto.feedlike.FeedLikeRequsetDto;
import speedit.bookplate.exception.NotExistCodeException;
import speedit.bookplate.service.FeedService;
import speedit.bookplate.utils.JwtService;
import speedit.bookplate.utils.enumTypes.Code;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    private final JwtService jwtService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> createFeed(@RequestBody @Valid FeedCreateRequestDto feedCreateRequestDto) {
            jwtService.isExpireAccessToken();
            long userIdx = jwtService.getUserIdx();
            feedService.createFeed(userIdx,feedCreateRequestDto);
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
    public ResponseEntity<List<FeedResponseDto>> getFeed(@RequestParam(value = "bookdIdx",required = false) Long bookIdx,
                                                            @RequestParam(value = "code",required = false) Code code,
                                                            @RequestParam(value = "job",required = false) String job){
            if(!Code.isExistCode(code)){
                throw new NotExistCodeException();
            }
            jwtService.isExpireAccessToken();
            long userIdx = jwtService.getUserIdx();

            List<FeedResponseDto> searchFeedRes = feedService.getFeed(userIdx,bookIdx,code,job)
                    .stream().map(v-> FeedResponseDto.SearchFeedResDtoToEntity(v))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(searchFeedRes);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<FeedResponseDto>> searchFeed(){

        List<FeedResponseDto> searchFeedRes = feedService.getFeeds();

        return ResponseEntity.ok().body(searchFeedRes);
    }

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> likeFeed(@RequestBody @Valid FeedLikeRequsetDto feedLikeRequsetDto)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedService.likeFeed(jwtService.getUserIdx(), feedLikeRequsetDto));
    }

    @RequestMapping(value = "/CancelLikeFeed",method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> cancelLikeFeed(@RequestBody @Valid FeedLikeRequsetDto feedLikeRequsetDto)  {
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok().body(feedService.cancelLikeFeed(feedLikeRequsetDto));
    }

}
