package speedit.bookplate.dto.feed;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 피드 등록하기 api request dto
 */
@Data
public class FeedRequestDto {

    @NotBlank(message = "문장정보를 입력해주세요")
    @ApiModelProperty(value = "문장정보", required = true)
    private String contents;

    @NotNull(message = "선택한 책 고유번호를 입력해주세요")
    @ApiModelProperty(value = "선택한 책 고유번호", required = true)
    private Long bookIdx;

    @ApiModelProperty(value = "피드에 대한 의견", required = true)
    private String opinion;

    @NotNull(message = "피드 정렬에 대한 값을 입력해주세요")
    @ApiModelProperty(value = "피드 정렬정보",required = true)
    private String sort;

    @NotNull(message = "피드 배경색1을 입력해주세요")
    @ApiModelProperty(value = "피드 배경색1", required = true)
    private String color1;

    @NotNull(message = "피드 배경색2을 입력해주세요")
    @ApiModelProperty(value = "피드 배경색2", required = true)
    private String color2;

    @NotNull(message = "나만 보기 여부를 입력해주세요")
    @ApiModelProperty(value = "나만 보기 여부", required = true)
    private Boolean isPrivate;


}
