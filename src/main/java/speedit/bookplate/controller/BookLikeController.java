package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.service.BookLikeService;
import speedit.bookplate.dto.booklike.BookLikeRequestDto;
import speedit.bookplate.utils.JwtService;

import javax.validation.Valid;

@Api(tags = {"12.BookLike"})
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BookLikeController {

    private final BookLikeService bookLikeService;
    private final JwtService jwtService;

    @ApiOperation(value = "도서 좋아요 하기", notes = "특정한 도서를 좋아요 한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "도서 좋아요에 성공하였습니다."),
            @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/likeBook",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> likeBook(@RequestBody @Valid BookLikeRequestDto bookLikeRequestDto) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        bookLikeService.likeBook(userIdx, bookLikeRequestDto);
        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @ApiOperation(value = "도서 좋아요 취소하기", notes = "특정한 도서를 좋아를 취소 한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "도서 좋아요에 성공하였습니다."),
            @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/cancelLikeBook",method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> cancelLikeBook(@RequestBody @Valid BookLikeRequestDto bookLikeRequestDto) {
        jwtService.isExpireAccessToken();
        bookLikeService.cancelLikeBook(bookLikeRequestDto);
        return ResponseEntity.ok().body(new CommonResponseDto());
    }
}
