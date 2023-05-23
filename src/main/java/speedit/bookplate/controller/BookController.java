package speedit.bookplate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.book.*;
import speedit.bookplate.service.BookService;
import speedit.bookplate.utils.JwtService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Api(tags = {"3. Book"})
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final JwtService jwtService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ApiOperation(value = "책 생성하기",notes = "새로운 책을 생성한다.")
    public ResponseEntity<CommonResponseDto> createBook(@RequestBody BookReqDto bookReqDto){
        bookService.createBook(bookReqDto);
        return ResponseEntity.ok(new CommonResponseDto());
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ApiOperation(value = "네이버 책 API에서 책 조회",notes = "네이버 책 API에서 책을 조회합니다.")
    public ResponseEntity<List<SearchBookResDto>> searchBookNaver(@RequestParam String query, @RequestParam int start) {
        List<SearchBookResDto> searchBooks = bookService.searchBook(query,start);
        return ResponseEntity.ok(searchBooks);
    }


    @RequestMapping(value = "/{isbn}",method = RequestMethod.GET)
    @ApiOperation(value = "책 고유번호로 책 조회",notes = "책 고유번호로 책을 조회합니다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    public ResponseEntity<BookDetailResDto> getBookDetail(@PathVariable Long isbn){
        long userIdx = jwtService.getUserIdx();
        return ResponseEntity.ok(bookService.getBookDetail(isbn,userIdx));
    }


    @ApiOperation(value = "책 좋아요",notes = "책을 좋아요한다.")
    @RequestMapping(value = "/{bookIdx}/likes",method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    public ResponseEntity<BookLikeResponseDto> likeBook(@PathVariable final Long bookIdx) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        BookLikeResponseDto bookLikeResponseDto = bookService.likeBook(userIdx, bookIdx);
        return ResponseEntity.ok().body(bookLikeResponseDto);
    }

    @ApiOperation(value = "책 좋아요 취소",notes = "책 좋아요를 취소한다.")
    @RequestMapping(value = "/{bookIdx}/likes",method = RequestMethod.DELETE)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt",value = "JWT Token",required = true,dataType = "string",paramType = "header")})
    public ResponseEntity<BookLikeResponseDto> cancelLikeBook(@PathVariable final Long bookIdx) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        BookLikeResponseDto bookLikeResponseDto = bookService.cancelLikeBook(userIdx,bookIdx);
        return ResponseEntity.ok().body(bookLikeResponseDto);
    }


}
