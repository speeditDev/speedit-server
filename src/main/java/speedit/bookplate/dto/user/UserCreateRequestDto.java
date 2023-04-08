package speedit.bookplate.dto.user;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import speedit.bookplate.utils.enumTypes.Gender;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequestDto {

    @ApiParam(value = "회원 닉네임",required = true, example = "speedit")
    private String nickname;

    private String password;

    @ApiParam(value = "회원 프로필 사진",required = true, example = "speedit")
    private String profileImg;

    @NotNull(message = "생년월일을 입력해주세요")
    private String birth;

    @NotNull(message = "성별을 입력해주세요")
    private Gender gender;

    @NotEmpty(message = "직업을 입력해주세요")
    private String job;

    private String company;

    private String personalEmail;

}
