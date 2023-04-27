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
    private final FollowingRepository followingRepository;
    private final FeedLikeRepository feedLikeRepository;

    public List<Feed> getFeed(Long userIdx,Long bookIdx,Code code,String job){
        User user = userRepository.findById(userIdx)
                .orElseThrow(()->new NotExistUserException());

        List<Feed> feeds = feedRepository.findAllByOrderByIdDesc();
        List<Feed> userFeeds = user.getFeeds();
        if (code.equals(Code.B)) {
            Book book = bookRepository.findById(bookIdx)
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
        List<Following> a = followingRepository.findByFollowingId(followerIdx);
        List<Feed> array = new ArrayList<>();
        /*
        a.stream().map(v->
                array.addAll(feedRepository.findById(v.getFollowingId()))
        );*/
        return array;
    }

    public void createFeed(Long userIdx, FeedCreateRequestDto regFeedRequestDto) {
        Book book = bookRepository.findById(regFeedRequestDto.getBookIdx())
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
