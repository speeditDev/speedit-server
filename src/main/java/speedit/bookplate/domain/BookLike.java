package speedit.bookplate.domain;

import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   //책 좋아요 고유번호

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "book_Id")
    private Long bookId;

    @Column(nullable = false)
    private Boolean isLiked;    //좋아요 클릭 여부

    public static BookLike createLike(Long userId, Long bookId) {
        return BookLike.builder()
                .userId(userId)
                .bookId(bookId)
                .isLiked(true)
                .build();
    }

    public void cancelLikeBook(Boolean isLiked){
        this.isLiked = isLiked;
    }
}
