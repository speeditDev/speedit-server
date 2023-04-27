package speedit.bookplate.dto.book;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverBookResDto {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

}
