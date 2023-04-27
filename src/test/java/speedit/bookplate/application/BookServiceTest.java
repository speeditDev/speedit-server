package speedit.bookplate.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import speedit.bookplate.dto.book.BookReqDto;
import speedit.bookplate.exception.DuplicateBookException;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.service.BookService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 DB에 Insert시 이미 존재하는 책이면 DuplicateBookException 에러 반환하도록 수정")
    void 책생성_이미존재하는책(){
        BookReqDto bookReqDto = new BookReqDto(123L,"은영","은영2","카테고리","https://ages.png","설명설명","히히히히","2021.13.21");

        when(bookRepository.existsByIsbn(any())).thenReturn(true);

        Assertions.assertThrows(DuplicateBookException.class,() -> bookService.createBook(bookReqDto));

    }


}
