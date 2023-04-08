package speedit.bookplate.dto.booklike;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 도서 좋아요 하기 api request dto
 */
@Data
public class BookLikeRequestDto {

    @NotNull(message = "좋아요할 도서 고유번호를 입력해주세요")
    @ApiModelProperty(value = "좋아요한 도서 고유번호", required = true)
    private Long bookIdx;

}
