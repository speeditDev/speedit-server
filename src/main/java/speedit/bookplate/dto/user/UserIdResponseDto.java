package speedit.bookplate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserIdResponseDto {

    private String id;
    private LocalDateTime createdAt;

}
