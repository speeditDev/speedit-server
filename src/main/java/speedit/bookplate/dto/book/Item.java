package speedit.bookplate.dto.book;

import lombok.Data;


@Data
public class Item {
    private String title;       //책 제목
    private String link;
    private String image;
    private String author;
    private int discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
}
