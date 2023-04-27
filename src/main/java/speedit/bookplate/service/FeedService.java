package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.domain.*;
import speedit.bookplate.dto.feed.*;
import speedit.bookplate.exception.*;
import speedit.bookplate.repository.*;
import speedit.bookplate.utils.enumTypes.Code;

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
    private final FeedLikeRepository feedLikeRepository;

    public List<FeedResponseDto> getFeed(Long userIdx,Long bookIdx,Code code,String job){
        User user = userRepository.findById(userIdx)
                .orElseThrow(()->new NotExistUserException());

        List<Feed> feeds = null;
        List<FeedResponseDto> resultFeeds = new ArrayList<>();

        List<Feed> userFeeds = user.getFeeds();

        if (code.equals(Code.B)) {
            Book book = bookRepository.findById(bookIdx)
                    .orElseThrow(()->new NotFoundBookIdxException());
            Collections.sort(book.getFeeds(),(feed1,feed2)-> (int) (feed2.getId()-feed1.getId()));
            feeds=feedRepository.findFollowingUserFeed(userIdx)
                    .orElseThrow(()->new NotFoundFeedException());
        } else if (code.equals(Code.M)) {
            feeds=feedRepository.findAllByUserId(userIdx)
                    .orElseThrow(()->new NotFoundFeedException());
        } else if (code.equals(Code.J)) {
            //이쪽 부분 다시 수정
            feeds=feedRepository.findFollowingUserFeed(userIdx)
                    .orElseThrow(()->new NotFoundFeedException());
        } else if (code.equals(Code.F)) {
            feeds=feedRepository.findFollowingUserFeed(userIdx)
                    .orElseThrow(()->new NotFoundFollowingUserFeedException());
        } else{
            feeds = feedRepository.findAllByOrderByIdDesc();
        }

        feeds.stream().forEach(v->resultFeeds.add(FeedResponseDto.of(v)));

        return resultFeeds;
    }


    public void createFeed(Long userIdx, FeedRequestDto feedRequestDto) {
        Book book = bookRepository.findById(feedRequestDto.getBookIdx())
                .orElseThrow(()-> new NotFoundBookIdxException());
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotExistUserException());
        Feed feed = new Feed(feedRequestDto,user,book);

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
                .stream().map(v -> FeedResponseDto.of(v))
                .collect(Collectors.toList());
    }

    public FeedLikeResponseDto likeFeed(Long userIdx, Long feedIdx) {
        final Feed feed = feedRepository.findById(feedIdx)
                .orElseThrow(()-> new NotFoundFeedException());
        if(feedLikeRepository.existsByFeedIdAndUserId(feedIdx,userIdx)){
            throw new InvalidLikeBookException();
        }
        feed.like();
        feedLikeRepository.save(new FeedLike(userIdx,feedIdx));
        return new FeedLikeResponseDto(feed.getLikes(),true);
    }

    public FeedLikeResponseDto cancelLikeFeed(Long userIdx,Long feedIdx) {
        final Feed feed = feedRepository.findById(feedIdx)
                .orElseThrow(()-> new NotFoundFeedException());
        final FeedLike feedLike = feedLikeRepository.findByFeedIdAndUserId(feedIdx,userIdx)
                .orElseThrow(()->new InvalidCancelLikeBookException());
        feed.cancelLike();
        feedLikeRepository.delete(feedLike);
        return new FeedLikeResponseDto(feed.getLikes(),false);
    }


}
