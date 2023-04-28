package speedit.bookplate.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import speedit.bookplate.dto.book.BookReqDto;
import speedit.bookplate.exception.DuplicateBookException;
import speedit.bookplate.exception.InvalidCancelLikeBookException;
import speedit.bookplate.exception.NotFoundBookIdxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest extends ServiceTest{

    @Test
    @DisplayName("책 DB에 Insert시 이미 존재하는 책이면 DuplicateBookException 에러 반환하도록 수정")
    void 책생성_이미존재하는책(){
        BookReqDto bookReqDto = new BookReqDto(123L,"은영","은영2","카테고리","https://ages.png","설명설명","히히히히","2021.13.21");

        when(bookRepository.existsByIsbn(any())).thenReturn(true);

        Assertions.assertThrows(DuplicateBookException.class,() -> bookService.createBook(bookReqDto));

    }

    @Test
    @DisplayName("존재하지 않는 책 조회시 NotFoundBookIdxException 에러 반환하도록 수정")
    void 존재하지_않는_책조회(){

        when(bookRepository.findByIsbn(any())).thenThrow(new NotFoundBookIdxException());

        Assertions.assertThrows(NotFoundBookIdxException.class,()->bookService.getBookDetail(1l,1l));
    }


    @Test
    @DisplayName("책 좋아요 취소를 할 수 없을시 InvalidCancelBookException 에러 반환하도록 수정")
    void 존재하지_않는_책좋아요취소(){

        when(bookLikeRepository.findByUserIdAndBookId(any(),any())).thenThrow(new InvalidCancelLikeBookException());

        Assertions.assertThrows(InvalidCancelLikeBookException.class,()->bookLikeRepository.findByUserIdAndBookId(1l,1l));
    }


}
