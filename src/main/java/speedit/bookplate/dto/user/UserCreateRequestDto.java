package speedit.bookplate.dto.user;

import io.swagger.annotations.ApiParam;
import lombok.*;
import speedit.bookplate.utils.enumTypes.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequestDto {

    @NonNull
    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String nickname;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,12}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 12자의 비밀번호여야 합니다.")
    private String password;

    private String profileImg;

    @NonNull
    @NotBlank(message = "생년월일은 필수 입력사항 입니다.")
    private String birth;

    @NonNull
    private Gender gender;

    @NonNull
    @NotBlank(message = "직업은 필수 입력사항 입니다.")
    private String job;

    private String company;

    private String personalEmail;

}
