package speedit.bookplate.dto.book;

import lombok.Data;

import java.util.HashMap;


@Data
public class Item {
    private String title;       //책 제목
    private String link;
    private String author;      //작가명
    private String pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private int itemId;
    private int priceSales;
    private int priceStandard;
    private String mailType;
    private String stockStatus;
    private int mileage;
    private String cover;
    private int categoryId;
    private String categoryName;
    private String publisher;
    private int salesPoint;
    private Boolean adult;
    private Boolean fixedPrice;
    private int customerReviewRank;
    private HashMap subInfo;
}
