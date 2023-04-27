package speedit.bookplate.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import speedit.bookplate.domain.Feed;

@Builder
@AllArgsConstructor
@Data
public class FeedResponseDto {
    private String profileImg;
    private String nickname;
    private String job;
    private String company;
    private Long feedIdx;     //피드 고유번호
    private String contents;    //피드 내용
    private Boolean isLiked;    //피드 좋아요 쿨락 여부
    private Boolean isMine; //내것인지 아닌지 여부
    private Boolean isPrivate;  //나만 보기 피드 여부
    private String sort;
    private String color1;    //피드 백그라운드 컬러1
    private String color2;    //피드 백그라운드 컬러2
    private String opinion;  //피드 의견
    private Integer likeCnt; //피드 등록 문장 좋아요 개수
    private String bookName; //책 이름
    private Long bookIdx; //책 고유번호
    private String thumbnail; //책 표지
    private String author;   //작가 이름

    public static FeedResponseDto of(final Feed feed){
        return FeedResponseDto.builder()
                .profileImg(feed.getUser().getProfileImg())
                .nickname(feed.getUser().getNickname())
                .job(feed.getUser().getJob())
                .company(feed.getUser().getCompany())
                .feedIdx(feed.getId())
                .contents(feed.getContents())
                .isMine(false)
                .isPrivate(false)
                .sort(feed.getSort())
                .color1(feed.getColor1())
                .color2(feed.getColor2())
                .opinion(feed.getOpinion())
                .bookName(feed.getBook().getTitle())
                .bookIdx(feed.getBook().getId())
                .thumbnail(feed.getBook().getThumbnail())
                .author(feed.getBook().getAuthor())
                .build();
    }
}
