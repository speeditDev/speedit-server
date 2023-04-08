package speedit.bookplate.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import speedit.bookplate.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class StorageBookReqDto {

    @NotBlank(message = "책 고유번호를 입력해주세요")
    @ApiModelProperty(value = "책 고유번호", required = true)
    private Long itemId;

    @NotBlank(message = "책 제목을 입력해주세요")
    @ApiModelProperty(value = "책 제목", required = true)
    private String bookName;

    @NotBlank(message = "작가명 입력해주세요")
    @ApiModelProperty(value = "작가명", required = true)
    private String author;

    @NotBlank(message = "알라딘 카테고리를 입력해주세요")
    @ApiModelProperty(value = "알라딘 카테고리", required = true)
    private String aladinCategory;

    @NotNull(message = "알라딘 카테고리 아이디을 입력해주세요")
    @ApiModelProperty(value = "알라딘 카테고리 아이디", required = true)
    private Integer categoryId;

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

    public static Book bookDtoToEntity(StorageBookReqDto storageBookReqDto){
        return Book.builder()
                .itemId(storageBookReqDto.getItemId())
                .author(storageBookReqDto.getAuthor())
                .name(storageBookReqDto.getBookName())
                .publisher(storageBookReqDto.getPublisher())
                .releaseDate(storageBookReqDto.getReleaseDate())
                .categoryId(storageBookReqDto.getCategoryId())
                .thumbnail(storageBookReqDto.getThumbnail())
                .description(storageBookReqDto.getDescription())
                .aladinCategory(storageBookReqDto.getAladinCategory())
                .build();
    }

}
