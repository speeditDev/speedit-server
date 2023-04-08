package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.dto.feed.*;
import speedit.bookplate.exception.NotExistUserException;
import speedit.bookplate.exception.NotFoundBookIdxException;
import speedit.bookplate.exception.NotFoundFeedException;
import speedit.bookplate.domain.Book;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.utils.enumTypes.Code;
import speedit.bookplate.repository.FeedRepository;
import speedit.bookplate.repository.FollowRepository;
import speedit.bookplate.domain.Following;
import speedit.bookplate.domain.User;
import speedit.bookplate.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<Feed> getFeed(Long userIdx,Long bookIdx,Code code,String job){
        User user = userRepository.findById(userIdx)
                .orElseThrow(()->new NotExistUserException());

        List<Feed> feeds = feedRepository.findAllByOrderByIdDesc();
        List<Feed> userFeeds = user.getFeeds();
        if (code.equals(Code.B)) {
            Book book = bookRepository.findByItemId(bookIdx)
                    .orElseThrow(()->new NotFoundBookIdxException());
            Collections.sort(book.getFeeds(),(feed1,feed2)-> (int) (feed2.getId()-feed1.getId()));
            return book.getFeeds();
        } else if (code.equals(Code.M)) {
            return userFeeds;
        } else if (code.equals(Code.J)) {
            //이쪽 부분 다시 수정
            return userFeeds;
        } else if (code.equals(Code.F)) {
            return getFollowingUsers(userIdx);
        } else{
            return feeds;
        }
    }


    public List<Feed> getFollowingUsers(long followerIdx) {
        List<Following> a = followRepository.findByFollowingId(followerIdx);
        List<Feed> array = new ArrayList<>();
        /*
        a.stream().map(v->
                array.addAll(feedRepository.findById(v.getFollowingId()))
        );*/
        return array;
    }

    public void createFeed(Long userIdx, FeedCreateRequestDto regFeedRequestDto) {
        Book book = bookRepository.findByItemId(regFeedRequestDto.getBookIdx())
                .orElseThrow(()-> new NotFoundBookIdxException());
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotExistUserException());
        Feed feed = FeedCreateRequestDto.FeedDtoToEntity(user,book,regFeedRequestDto);
        feedRepository.save(feed);
    }

    public void deleteFeed(Long feedIdx) {
        Feed feed = feedRepository.findById(feedIdx)
                        .orElseThrow(() -> new NotFoundFeedException());
        feed.updateFeedStatus();
    }

    public void updateFeed(FeedUpdateRequestDto amdFeedUpdateRequestDto) {
        Feed feed = feedRepository.findById(amdFeedUpdateRequestDto.getFeedIdx())
                .orElseThrow(()->new NotFoundFeedException());
        feed.updateFeed(amdFeedUpdateRequestDto);
    }

    public List<FeedResponseDto> getFeeds(){
        return feedRepository.findAllBy()
                .stream().map(v -> FeedResponseDto.SearchFeedResDtoToEntity(v))
                .collect(Collectors.toList());
    }


}
