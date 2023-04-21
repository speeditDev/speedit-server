package speedit.bookplate.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookLikeResponseDto {

    private Long likes;
    private Boolean liked;

}
