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
    private Long isbn;
    private String title;
    private String author;
    private String thumbnail;
    private String category;
    private String releaseDate;
    private String publisher;
    private String description;
    private boolean isLiked;
    private List<BookDetailFeedResDto> bookDetailFeedResDtoList;

    public static BookDetailResDto convertBookDetailRes(Book book, List<Feed> feed,boolean isLiked){
        return BookDetailResDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .thumbnail(book.getThumbnail())
                .category(book.getCategory())
                .releaseDate(book.getReleaseDate())
                .publisher(book.getPublisher())
                .description(book.getDescription())
                .isLiked(isLiked)
                .build();
    }

}
