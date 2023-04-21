package speedit.bookplate.domain;


import lombok.NoArgsConstructor;
import speedit.bookplate.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class BookLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   //책 좋아요 고유번호

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "book_Id")
    private Long bookId;


    public BookLike(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}
