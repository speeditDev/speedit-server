package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.exception.InvalidLikeMessageException;
import speedit.bookplate.dto.feedlike.FeedLikeRequsetDto;
import speedit.bookplate.domain.FeedLike;
import speedit.bookplate.repository.FeedLikeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedLikeService {

    private final FeedLikeRepository feedLikeRepository;

    public CommonResponseDto likeFeed(Long userIdx, FeedLikeRequsetDto feedLikeRequsetDto) {

        if(feedLikeRepository.existsByFeedIdAndUserId(feedLikeRequsetDto.getFeedIdx(),userIdx)){
            throw new InvalidLikeMessageException();
        }

        feedLikeRepository.save(FeedLike.createLike(userIdx, feedLikeRequsetDto.getFeedIdx()));
        return new CommonResponseDto();
    }

    public CommonResponseDto cancelLikeFeed(FeedLikeRequsetDto feedLikeRequsetDto) {
        FeedLike feedLike = feedLikeRepository.findByFeedId(feedLikeRequsetDto.getFeedIdx());
        feedLike.cancelLikeFeed(false);
        return new CommonResponseDto();
    }

}
