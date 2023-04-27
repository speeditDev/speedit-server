package speedit.bookplate.controller;

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
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final JwtService jwtService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> storageBook(@RequestBody StorageBookReqDto storageBookReqDto){
        jwtService.isExpireAccessToken();

        bookService.storageBook(storageBookReqDto);
        return ResponseEntity.ok(new CommonResponseDto());
    }


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<List<SearchBookResDto>> searchBookAladin(@RequestParam String query, @RequestParam int start) {
        List<SearchBookResDto> searchBooks = bookService.searchBook(query,start);
        return ResponseEntity.ok(searchBooks);
    }

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

    @RequestMapping(value = "/{bookIdx}/likes",method = RequestMethod.POST)
    public ResponseEntity<BookLikeResponseDto> likeBook(@PathVariable final Long bookIdx) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        BookLikeResponseDto bookLikeResponseDto = bookService.likeBook(userIdx, bookIdx);
        return ResponseEntity.ok().body(bookLikeResponseDto);
    }

    @RequestMapping(value = "/{bookIdx}/likes",method = RequestMethod.DELETE)
    public ResponseEntity<BookLikeResponseDto> cancelLikeBook(@PathVariable final Long bookIdx) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        BookLikeResponseDto bookLikeResponseDto = bookService.cancelLikeBook(userIdx,bookIdx);
        return ResponseEntity.ok().body(bookLikeResponseDto);
    }


}
