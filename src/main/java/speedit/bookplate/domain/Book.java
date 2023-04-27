package speedit.bookplate.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;
import speedit.bookplate.utils.enumTypes.BookCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;   //책 이름

    @Column(nullable = false)
    private String author;     //작가명

    private String category;   //책그릇 카테고리명

    private String publisher;   //출판사

    private String releaseDate; //출간일

    private String description;   //책 설명

    private String thumbnail;  //책 이미지

    @JsonManagedReference
    @OneToMany(mappedBy = "book")
    private List<Feed> feeds = new ArrayList<>();   //피드 정보

    private Long isbn; //책 고유번호

    private Long likes;

    public void like() {likes = likes +1; }

    public void cancelLike() {likes = likes-1; }


}
