package speedit.bookplate.config;

import lombok.Getter;

@Getter
public class CommonResponseDto {

    private String message;

    public CommonResponseDto() {
        this.message = "success";
    }
}
