package speedit.bookplate.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedit.bookplate.dto.book.Item;

import java.util.List;

/**
 * 도서 검색하기 api response dto
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchBookResDto {
     private List<Item> content;
     private int totalResults;
     private int page;
}
