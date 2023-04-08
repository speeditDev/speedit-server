package speedit.bookplate.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 도서 검색하기 api request dto
 */
@Data
public class SearchBookReqDto {

    @NotBlank(message = "검색어를 입력해주세요")
    @ApiModelProperty(value = "검색어", required = true)
    private String searchWord;
    @NotBlank(message = "책 카테고리를 입력해주세요")
    @ApiModelProperty(value = "책 카테고리", required = true)
    private String category;

}
