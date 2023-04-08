package speedit.bookplate.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.Feed;

import java.util.List;

/**
 * 도서 상세정보 조회 api response dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailResDto {
    private Long itemId;
    private String bookName;
    private String author;
    private String thumbnail;
    private String category;
    private String releaseDate;
    private String publisher;
    private String description;
 //   private Boolean bookLike;
}
