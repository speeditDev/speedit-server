package speedit.bookplate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.book.BookDetailResDto;
import speedit.bookplate.dto.book.GetDetailResDto;
import speedit.bookplate.dto.book.SearchBookResDto;
import speedit.bookplate.dto.book.StorageBookReqDto;
import speedit.bookplate.service.BookService;
import speedit.bookplate.utils.JwtService;

@Api(tags = {"10.Book"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/Book")
public class BookController {

    private final BookService bookService;
    private final JwtService jwtService;

    @ApiOperation(value = "도서 데이터 디비에 저장", notes = "선택한 도서를 디비에 업데이트한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "알라딘 도서 디비 저장에 성공하였습니다."),
           @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> storageBook(@RequestBody StorageBookReqDto storageBookReqDto){
        jwtService.isExpireAccessToken();

        bookService.storageBook(storageBookReqDto);
        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "알라딘 open api 도서 검색하기", notes = "알라딘 open api를 사용하여 특정 도서 정보를 얻는다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "도서 검색에에 성공하였습니다."),
            @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<SearchBookResDto> searchBookAladin(@RequestParam String query, int page, int count) {
        jwtService.isExpireAccessToken();

        SearchBookResDto foundedBooks = bookService.searchBook(page, query, count);
        return ResponseEntity.ok(foundedBooks);
    }

    @ApiOperation(value = "도서 상세 정보 조회", notes = "특정한 도서의 상세 정보를 조회한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "도서 상세 정보 조회에 성공하였습니다."),
            @ApiResponse(code = 401, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 402, message = "유효하지 않은 JWT입니다.")
    })
    @RequestMapping(value = "/aladin/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<GetDetailResDto> getAladinBookDetail(@PathVariable Long itemId){
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok(bookService.getAladinBookDetail(itemId));
    }

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<BookDetailResDto> getBookDetail(@PathVariable Long itemId){
        jwtService.isExpireAccessToken();
        return ResponseEntity.ok(bookService.getBookDetail(itemId));
    }


}
