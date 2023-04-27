package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.BookLike;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.dto.book.*;
import speedit.bookplate.exception.DuplicateBookException;
import speedit.bookplate.exception.InvalidCancelLikeBookException;
import speedit.bookplate.exception.InvalidLikeBookException;
import speedit.bookplate.exception.NotFoundBookIdxException;
import speedit.bookplate.repository.BookLikeRepository;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.repository.FeedRepository;
import speedit.bookplate.utils.AladinFeignClient;
import speedit.bookplate.utils.NaverSearchFeignClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final FeedRepository feedRepository;
    private final AladinFeignClient aladinFeignClient;
    private final NaverSearchFeignClient naverSearchFeignClient;
    private final BookLikeRepository bookLikeRepository;

    public void storageBook(StorageBookReqDto storageBookReqDto) {
        if(bookRepository.existsByItemId(storageBookReqDto.getItemId())==true){
            throw new DuplicateBookException();
        }

        bookRepository.save(StorageBookReqDto.bookDtoToEntity(storageBookReqDto));
    }


    public List<SearchBookResDto> searchBook(String query,int start) {
        NaverBookResDto searchList = naverSearchFeignClient.getBookDetail(query,start);

        List<SearchBookResDto> resultList = new ArrayList<>();

        searchList.getItems().stream()
                .forEach(v-> resultList.add(new SearchBookResDto(v.getTitle(),v.getAuthor(),v.getImage())));

        return resultList;
    }


    public GetDetailResDto getAladinBookDetail(Long bookIdx){
        aladinFeignClient.getBookDetail(bookIdx);
        GetDetailResDto res = new GetDetailResDto();

        return res;
    }


    public BookDetailResDto getBookDetail(Long bookIdx){
        Book book = bookRepository.findByItemId(bookIdx)
                .orElseThrow(()->new NotFoundBookIdxException());
        List<Feed> feeds = feedRepository.findByBook(book);

        BookDetailResDto bookDetailResDto = new BookDetailResDto();
        return bookDetailResDto;
    }


    public BookLikeResponseDto likeBook(Long userIdx, Long bookIdx) {
        final Book book = bookRepository.findById(bookIdx)
                        .orElseThrow(()->new NotFoundBookIdxException());
        if(bookLikeRepository.existsByUserIdAndBookId(userIdx,bookIdx)){
            throw new InvalidLikeBookException();
        }
        book.like();
        bookLikeRepository.save(new BookLike(userIdx,bookIdx));
        return new BookLikeResponseDto(book.getLikes(),true);
    }

   
    public BookLikeResponseDto cancelLikeBook(Long userIdx, Long bookIdx) {
        final Book book = bookRepository.findById(bookIdx)
                .orElseThrow(()->new NotFoundBookIdxException());
        final BookLike bookLike = bookLikeRepository.findByUserIdAndBookId(userIdx,bookIdx)
                .orElseThrow(()->new InvalidCancelLikeBookException());
        book.cancelLike();
        bookLikeRepository.delete(bookLike);
        return new BookLikeResponseDto(book.getLikes(),false);
    }

}
