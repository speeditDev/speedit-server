package speedit.bookplate.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Content {
    private String bookName;
    private long itemId;
    private String author;
    private String publisher;
    private String aladinCategory;
    private Integer categoryId;
    private String thumbnail;
    private String releaseDate;
    private String description;
}

