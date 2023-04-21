package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.BookLike;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.dto.book.*;
import speedit.bookplate.dto.booklike.BookLikeRequestDto;
import speedit.bookplate.exception.DuplicateBookException;
import speedit.bookplate.exception.NotFoundBookIdxException;
import speedit.bookplate.repository.BookLikeRepository;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.repository.FeedRepository;
import speedit.bookplate.utils.AladinFeignClient;
import speedit.bookplate.utils.AladinSearchFeignClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final FeedRepository feedRepository;
    private final AladinFeignClient aladinFeignClient;
    private final AladinSearchFeignClient aladinSearchFeignClient;
    private final BookLikeRepository bookLikeRepository;

    @Transactional
    public void storageBook(StorageBookReqDto storageBookReqDto) {
        if(bookRepository.existsByItemId(storageBookReqDto.getItemId())==true){
            throw new DuplicateBookException();
        }

        bookRepository.save(StorageBookReqDto.bookDtoToEntity(storageBookReqDto));
    }

    @Transactional
    public SearchBookResDto searchBook(int page, String query, int count) {
        AladinResDto searchList = aladinSearchFeignClient.getBookDetail(query);

        SearchBookResDto resultList = new SearchBookResDto();

        resultList.setTotalResults(searchList.getTotalResults());

        List<Content> contents = new ArrayList<>();

        searchList.getItem().stream().map(v ->
                contents.add(
                        new Content(v.getTitle(),v.getItemId(),v.getAuthor(),v.getAuthor(),v.getCategoryName(),v.getCategoryId(),v.getCover(),v.getPubDate(),v.getDescription()))
                );

        resultList.setPage(page);
        resultList.setContent(searchList.getItem());

        return resultList;
    }

    @Transactional
    public GetDetailResDto getAladinBookDetail(Long bookIdx){
        aladinFeignClient.getBookDetail(bookIdx);
        GetDetailResDto res = new GetDetailResDto();

        return res;
    }

    @Transactional
    public BookDetailResDto getBookDetail(Long bookIdx){
        Book book = bookRepository.findByItemId(bookIdx)
                .orElseThrow(()->new NotFoundBookIdxException());
        List<Feed> feeds = feedRepository.findByBook(book);

        BookDetailResDto bookDetailResDto = new BookDetailResDto();
        return bookDetailResDto;
    }

    @Transactional
    public void likeBook(Long userIdx, BookLikeRequestDto bookLikeRequestDto) {
        bookLikeRepository.save(BookLike.createLike(userIdx, bookLikeRequestDto.getBookIdx()));
    }

    @Transactional
    public void cancelLikeBook(BookLikeRequestDto bookLikeRequestDto) {
        BookLike bookLike = bookLikeRepository.findByBookId(bookLikeRequestDto.getBookIdx());
    }
}
