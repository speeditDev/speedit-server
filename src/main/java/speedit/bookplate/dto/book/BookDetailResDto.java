package speedit.bookplate.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import speedit.bookplate.domain.Book;
import speedit.bookplate.domain.Feed;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailResDto {
    private Long itemId;
    private String bookName;
    private String author;
    private String thumbnail;
    private String category;
    private String releaseDate;
    private String publisher;
    private String description;
    private List<BookDetailFeedResDto> bookDetailFeedResDtoList;

    public static BookDetailResDto convertBookDetailRes(Book book, Feed feed){
        return BookDetailResDto.builder()
                .itemId(book.getItemId())
                .bookName(book.getName())
                .author(book.getAuthor())
                .thumbnail(book.getThumbnail())
                .category(book.getAladinCategory())
                .releaseDate(book.getReleaseDate())
                .publisher(book.getPublisher())
                .description(book.getDescription())
                .build();
    }

}
