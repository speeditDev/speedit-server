package speedit.bookplate.domain;

import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //책 카테고리 고유번호

    @ElementCollection()
    private Map<String,Boolean> bookCategory;   //책 카테고리, 선택 유무(ture : 선택, false : 미선택)

    public static BookCategory createBookCategory(Map<String,Boolean> bookCategory) {
        return BookCategory.builder()
                .bookCategory(bookCategory)
                .build();
    }
}
