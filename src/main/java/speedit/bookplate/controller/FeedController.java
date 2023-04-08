package speedit.bookplate.controller;

import io.swagger.annotations.*;
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
import java.util.stream.Collectors;

@Api(tags = {"2. Feed"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    private final JwtService jwtService;

    @ApiOperation(value = "피드 작성하기", notes = "문장정보, 도서 정보, 피드에 대한 의견, 피드 배경색, 나만 보기 여부를 입력해서 새로운 피드를 생성한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "피드 작성에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> createFeed(@RequestBody @Valid FeedCreateRequestDto feedCreateRequestDto) {
            jwtService.isExpireAccessToken();
            long userIdx = jwtService.getUserIdx();
            feedService.createFeed(userIdx,feedCreateRequestDto);
            return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "피드 삭제하기", notes = "특정한 피드를 삭제한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "피드 삭제에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/{feedIdx}",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> deleteFeed(@PathVariable long feedIdx) {
        jwtService.isExpireAccessToken();
        feedService.deleteFeed(feedIdx);

        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "피드 수정하기", notes = "특정한 피드 정보를 수정한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "피드 작성에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "",method = RequestMethod.PATCH)
    public ResponseEntity<CommonResponseDto> updateFeed(@RequestBody @Valid FeedUpdateRequestDto feedUpdateRequestDto) {
        jwtService.isExpireAccessToken();
        feedService.updateFeed(feedUpdateRequestDto);

        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "피드 조회하기", notes = "특정 책 또는 유저의 피드를 조회한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    @ApiResponses({
            @ApiResponse(code=200,message = "피드 조회에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다.")
    })
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

}
