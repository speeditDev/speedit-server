package speedit.bookplate.dto.feedlike;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 피드 좋아요 하기 api request dto
 */
@Data
public class FeedLikeRequsetDto {

    @NotNull(message = "좋아요할 피드 고유번호를 입력해주세요")
    @ApiModelProperty(value = "좋아요할 피드 고유번호", required = true)
    private Long feedIdx;

}
