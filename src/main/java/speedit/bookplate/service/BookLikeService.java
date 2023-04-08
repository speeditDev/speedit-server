package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.dto.booklike.BookLikeRequestDto;
import speedit.bookplate.domain.BookLike;
import speedit.bookplate.repository.BookLikeRepository;
import speedit.bookplate.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BookLikeService {

    private final BookLikeRepository bookLikeRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void likeBook(Long userIdx, BookLikeRequestDto bookLikeRequestDto) {
        bookLikeRepository.save(BookLike.createLike(userIdx, bookLikeRequestDto.getBookIdx()));
    }

    public void cancelLikeBook(BookLikeRequestDto bookLikeRequestDto) {
        BookLike bookLike = bookLikeRepository.findByBookId(bookLikeRequestDto.getBookIdx());
        bookLike.cancelLikeBook(false);
    }
}
