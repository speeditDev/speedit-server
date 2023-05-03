package speedit.bookplate.domain;

import lombok.*;
import speedit.bookplate.dto.feed.FeedRequestDto;
import speedit.bookplate.dto.feed.FeedUpdateRequestDto;
import speedit.bookplate.utils.enumTypes.Status;
import speedit.bookplate.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String contents;

    private String opinion;

    @Column(nullable = false)
    private String color1;

    @Column(nullable = false)
    private String color2;

    @Column(nullable = false)
    private Long likes;

    private String sort;

    private boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public void like() {likes = likes +1; }

    public void cancelLike() {likes = likes-1; }

    public void updateFeedStatus(){
        this.status=Status.N;
    }

    public void updateFeed(FeedUpdateRequestDto feedUpdateRequestDto){
        this.contents = feedUpdateRequestDto.getContents();
        this.color1 = feedUpdateRequestDto.getColor1();
        this.color2 = feedUpdateRequestDto.getColor2();
        this.opinion = feedUpdateRequestDto.getOpinion();
    }

    public Feed(FeedRequestDto feedRequestDto, User user, Book book) {
        this.id = feedRequestDto.getBookIdx();
        this.status = Status.Y;
        this.contents = feedRequestDto.getContents();
        this.opinion = feedRequestDto.getOpinion();
        this.color1 = feedRequestDto.getColor1();
        this.color2 = feedRequestDto.getColor2();
        this.likes = 0l;
        this.sort = feedRequestDto.getSort();
        this.isPrivate = feedRequestDto.getIsPrivate();
        this.user = user;
        this.book = book;
    }
}

