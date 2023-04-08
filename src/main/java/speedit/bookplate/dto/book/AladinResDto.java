package speedit.bookplate.dto.book;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 알라딘 open api response dto
 */
@Data
public class AladinResDto {
    private String version;   //버전 정보
    private String logo;
    private String title;     //title : 알라딘 전체 신간 리스트 - 국내도서
    private String link;
    private String pubDate;
    private int totalResults; //전체 검색 갯수
    private int startIndex;
    private int itemsPerPage;
    private String query;
    private int searchCategoryId;
    private String searchCategoryName;
    private List<Item> item = new ArrayList<>();    //도서 정보
}
