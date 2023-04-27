package speedit.bookplate.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import speedit.bookplate.domain.Book;

import javax.validation.constraints.NotBlank;


@Data
public class BookReqDto {

    @NotBlank(message = "책 고유번호를 입력해주세요")
    @ApiModelProperty(value = "책 고유번호", required = true)
    private Long isbn;

    @NotBlank(message = "책 제목을 입력해주세요")
    @ApiModelProperty(value = "책 제목", required = true)
    private String title;

    @NotBlank(message = "작가명 입력해주세요")
    @ApiModelProperty(value = "작가명", required = true)
    private String author;

    @NotBlank(message = "카테고리를 입력해주세요")
    @ApiModelProperty(value = "카테고리", required = true)
    private String category;

    @NotBlank(message = "책 이미지를 입력해주세요")
    @ApiModelProperty(value = "책 이미지", required = true)
    private String thumbnail;

    @NotBlank(message = "책 설명을 입력해주세요")
    @ApiModelProperty(value = "책 설명", required = true)
    private String description;

    @NotBlank(message = "출판사를 입력해주세요")
    @ApiModelProperty(value = "출판사", required = true)
    private String publisher;

    @NotBlank(message = "출간일을 입력해주세요")
    @ApiModelProperty(value = "출간일", required = true)
    private String releaseDate;

    public static Book bookDtoToEntity(BookReqDto bookReqDto){
        return Book.builder()
                .isbn(bookReqDto.getIsbn())
                .author(bookReqDto.getAuthor())
                .title(bookReqDto.getTitle())
                .publisher(bookReqDto.getPublisher())
                .releaseDate(bookReqDto.getReleaseDate())
                .thumbnail(bookReqDto.getThumbnail())
                .description(bookReqDto.getDescription())
                .category(bookReqDto.getCategory())
                .build();
    }

}
