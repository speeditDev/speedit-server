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
import speedit.bookplate.dto.booklike.BookLikeRequestDto;
import speedit.bookplate.service.BookService;
import speedit.bookplate.utils.JwtService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Book")
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
    public ResponseEntity<SearchBookResDto> searchBookAladin(@RequestParam String query, int page, int count) {
        jwtService.isExpireAccessToken();

        SearchBookResDto foundedBooks = bookService.searchBook(page, query, count);
        return ResponseEntity.ok(foundedBooks);
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

    @RequestMapping(value = "/likeBook",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> likeBook(@RequestBody @Valid BookLikeRequestDto bookLikeRequestDto) {
        jwtService.isExpireAccessToken();
        long userIdx = jwtService.getUserIdx();
        bookService.likeBook(userIdx, bookLikeRequestDto);
        return ResponseEntity.ok().body(new CommonResponseDto());
    }

    @RequestMapping(value = "/cancelLikeBook",method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> cancelLikeBook(@RequestBody @Valid BookLikeRequestDto bookLikeRequestDto) {
        jwtService.isExpireAccessToken();
        bookService.cancelLikeBook(bookLikeRequestDto);
        return ResponseEntity.ok().body(new CommonResponseDto());
    }


}
